package edu.columbia.gskr.tweetsentimentanalyzer.domain;

import edu.columbia.gskr.tweetsentimentanalyzer.enums.SentimentType;

import java.util.Date;
import java.util.List;

/**
 * Created by saikarthikreddyginni on 4/20/15.
 */
public class TweetSentiment {

    private long id;
    private String userScreenName;
    private String userLocation;
    private String profileImageURL;
    private String statusText;
    private double latitude;
    private double longitude;
    private List<String> hashTags;
    private Date createdDate;
    private Date updatedDate;
    private String language;
    private boolean isMixed;
    private double score;
    private SentimentType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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
}
