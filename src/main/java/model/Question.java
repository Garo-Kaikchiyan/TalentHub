package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Question {
	private String question_title;
	private String user_email;
	private String user_name;
	private String question_text;
	private Date date_created;
	private ArrayList<Answer> answers;
	
	public Question(String question_title, String user_email, String question_text, String firstName, String lastName) {
		super();
		this.question_title = question_title;
		this.user_email = user_email;
		this.question_text = question_text;
		user_name = firstName + " " + lastName;
		date_created = Date.valueOf(LocalDate.now());
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
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

	public String getQuestion_text() {
		return question_text;
	}

	public void setQuestion_text(String question_text) {
		this.question_text = question_text;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	
}
