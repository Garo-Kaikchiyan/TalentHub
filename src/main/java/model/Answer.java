package model;

import java.sql.Date;

public class Answer {
	private String question_title;
	private String user_email;
	private String text;
	private Date date_created;
	private int likes;
	
	
	
	public Answer(String question_title, String user_email, String text) {
		super();
		this.question_title = question_title;
		this.user_email = user_email;
		this.text = text;
	}
	public String getQuestion_title() {
		return question_title;
	}
	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getText() {
		return text;
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
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	
}
