package edu.columbia.gskr.tweetsentimentanalyzer.domain;

import edu.columbia.gskr.tweetsentimentanalyzer.enums.SentimentType;

/**
 * Created by saikarthikreddyginni on 4/19/15.
 */
public class Sentiment {

    private long tweetId;
    private String language;
    private boolean isMixed;
    private double score;
    private SentimentType type;

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public void setIsMixed(boolean isMixed) {
        this.isMixed = isMixed;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public SentimentType getType() {
        return type;
    }

    public void setType(SentimentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sentiment{" +
                "tweetId=" + tweetId +
                ", language='" + language + '\'' +
                ", isMixed=" + isMixed +
                ", score=" + score +
                ", type=" + type +
                '}';
    }
}
