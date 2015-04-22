package entities;

import twitter4j.GeoLocation;

public class TweeterObj {
	
	private String user_id;
	private String user_name;
	private int tweet_followers_count;
	private String source;
	private String location;
	private String tweet_ID;
	private String tweet_text;
	private boolean exists;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getTweet_followers_count() {
		return tweet_followers_count;
	}
	public void setTweet_followers_count(int tweet_followers_count) {
		this.tweet_followers_count = tweet_followers_count;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTweet_ID() {
		return tweet_ID;
	}
	public void setTweet_ID(String tweet_ID) {
		this.tweet_ID = tweet_ID;
	}
	public String getTweet_text() {
		return tweet_text;
	}
	public void setTweet_text(String tweet_text) {
		this.tweet_text = tweet_text;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}

	

}
