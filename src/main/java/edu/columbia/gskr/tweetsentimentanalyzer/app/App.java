package edu.columbia.gskr.tweetsentimentanalyzer.app;

import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import com.amazonaws.services.sqs.model.Message;
import com.owlike.genson.Genson;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.Tweet;
import edu.columbia.gskr.tweetsentimentanalyzer.util.AmazonQueue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by saikarthikreddyginni on 4/17/15.
 */

public class App {

    public static final Logger LOGGER = Logger.getLogger(App.class.getCanonicalName());

    public static void main(String[] args) {

        final Properties alchemyProperties = new Properties();
        final InputStream input = App.class.getClassLoader().getResourceAsStream("alchemy.properties");
        final ExecutorService executorService = Executors.newFixedThreadPool(50);
        final Genson genson = new Genson();
        final Timer timer = new Timer();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down...Shutdown ExecutorService");
                executorService.shutdown();
                timer.cancel();
                System.out.println("Shutdown Successful");
            }
        });

        try {
            alchemyProperties.load(input);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IOException Occurred when getting the alchemy.properties", ex);
            System.exit(-1);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Message> messages = AmazonQueue.getMessage();
                if (messages != null) {
                    for (Message message : messages) {
                        Tweet tweet = genson.deserialize(message.getBody(), Tweet.class);
                        executorService.submit(new SQSWorker(tweet, alchemyProperties));
                        AmazonQueue.deleteMessage(message);
                    }
                }
            }
        }, 1000, 5000);
    }
}
