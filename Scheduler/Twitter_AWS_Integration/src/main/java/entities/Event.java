package entities;

import org.springframework.data.annotation.Id;

public class Event {
	
	@Id
	String tag;
	String name;
	
	boolean status;
	String[] users;
	int target_twitts;
	int twitt_limit;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public int getTarget_twitts() {
		return target_twitts;
	}
	public void setTarget_twitts(int target_twitts) {
		this.target_twitts = target_twitts;
	}
	public int getTwitt_limit() {
		return twitt_limit;
	}
	public void setTwitt_limit(int twitt_limit) {
		this.twitt_limit = twitt_limit;
	}
	

}
