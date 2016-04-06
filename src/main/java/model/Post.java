package model;

import java.sql.Date;

public class Post {
	private String string_title;
	private String user_email;
	private String post_title;
	private String post;
	private Date date_created;
	public Post(String string_title, String user_email, String post_title, String post) {
		super();
		this.string_title = string_title;
		this.user_email = user_email;
		this.post_title = post_title;
		this.post = post;
	}
	public String getString_title() {
		return string_title;
	}
	public void setString_title(String string_title) {
		this.string_title = string_title;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	
	
}
