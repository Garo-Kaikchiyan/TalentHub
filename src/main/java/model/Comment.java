package model;

import java.sql.Date;

public class Comment {
	private int comment_id;
	private int post_id;
	private String user_email;
	private String text;
	private Date date_created;

	public Comment(int post_id, String user_email, String text) {
		super();
		this.post_id = post_id;
		this.user_email = user_email;
		this.text = text;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getText() {
		return text;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

}
