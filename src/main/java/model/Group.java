package model;

import java.util.ArrayList;

public class Group {
	private String group_name;
	private String user_created;
	private ArrayList<User> members;
	private ArrayList<Post> posts;
	

	private String message;

	public Group(String group_name) {
		super();
		this.group_name = group_name;
	}

	public ArrayList<User> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

}
