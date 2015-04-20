package edu.columbia.gskr.tweetsentimentanalyzer.mybatis.mapper;


import edu.columbia.gskr.tweetsentimentanalyzer.domain.Sentiment;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.Tweet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public interface TweetMapper {

    public void insertSentiment(Sentiment sentiment);

    public void updateSentiment(Sentiment sentiment);

    public void deleteSentiment(Sentiment sentiment);

}
