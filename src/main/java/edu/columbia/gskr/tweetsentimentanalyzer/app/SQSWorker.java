package edu.columbia.gskr.tweetsentimentanalyzer.app;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.util.json.JSONObject;
import com.likethecolor.alchemy.api.Client;
import com.likethecolor.alchemy.api.call.AbstractCall;
import com.likethecolor.alchemy.api.call.SentimentCall;
import com.likethecolor.alchemy.api.call.type.CallTypeText;
import com.likethecolor.alchemy.api.entity.Response;
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity;
import com.owlike.genson.Genson;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.Sentiment;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.Tweet;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.TweetSentiment;
import edu.columbia.gskr.tweetsentimentanalyzer.enums.SentimentType;
import edu.columbia.gskr.tweetsentimentanalyzer.mybatis.service.TweetService;
import edu.columbia.gskr.tweetsentimentanalyzer.util.AmazonQueue;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by saikarthikreddyginni on 4/19/15.
 */
public class SQSWorker implements Runnable {

    private final Tweet tweet;
    private final String apiKey;
    private final Logger LOGGER = Logger.getLogger(SQSWorker.class.getCanonicalName());


    public SQSWorker(Tweet tweet, Properties alchemyProperties) {
        this.tweet = tweet;
        this.apiKey = alchemyProperties.getProperty("alchemy.apiKey");
    }

    public void run() {

        final Client client = new Client();
        client.setAPIKey(this.apiKey);
        try{
            LOGGER.info("Processing tweet in a SQS Worker with tweetId: " + tweet.getId());
            final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(new CallTypeText(tweet.getStatusText()));
            final Response<SentimentAlchemyEntity> sentimentResponse = client.call(sentimentCall);

            Sentiment sentiment = new Sentiment();
            sentiment.setLanguage(sentimentResponse.getLanguage());
            sentiment.setTweetId(tweet.getId());

            SentimentAlchemyEntity entity;
            final Iterator<SentimentAlchemyEntity> iter = sentimentResponse.iterator();
            while (iter.hasNext()) {
                entity = iter.next();
                sentiment.setIsMixed((entity.isMixed()));
                sentiment.setScore(entity.getScore());
                sentiment.setType(SentimentType.valueOf(entity.getType().toString()));
            }

            TweetService tweetService = new TweetService();
            tweetService.insertSentiment(sentiment);

            TweetSentiment tweetSentiment = new TweetSentiment();
            tweetSentiment.setId(tweet.getId());
            tweetSentiment.setUserScreenName(tweet.getUserScreenName());
            tweetSentiment.setUserLocation(tweet.getUserLocation());
            tweetSentiment.setProfileImageURL(tweet.getProfileImageURL());
            tweetSentiment.setStatusText(tweet.getStatusText());
            tweetSentiment.setLatitude(tweet.getLatitude());
            tweetSentiment.setLongitude(tweet.getLongitude());
            tweetSentiment.setHashTags(tweet.getHashTags());
            tweetSentiment.setCreatedDate(tweet.getCreatedDate());
            tweetSentiment.setUpdatedDate(tweet.getUpdatedDate());
            tweetSentiment.setLanguage(sentiment.getLanguage());
            tweetSentiment.setIsMixed(sentiment.isMixed());
            tweetSentiment.setScore(sentiment.getScore());
            tweetSentiment.setType(sentiment.getType());

            // Push the TweetSentiment Object to SNS
            Genson genson = new Genson();
            String output = genson.serialize(tweetSentiment);
            AmazonQueue.publishSNSMessage(output);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IOException Occurred when processing the tweet in a SQS Worker with tweetiD: " + tweet.getId(), ex);
        }
    }
}
