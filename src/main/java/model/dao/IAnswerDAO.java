package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Question;
import model.Answer;
import model.User;

public interface IAnswerDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}
	
	boolean addAnswer(Answer answer,Question question,User newUser);
	ArrayList<Answer> getAllAnswers(Question question) throws SQLException;
	ArrayList<Answer> getAllPosts(User newUser) throws SQLException;
	public void vote(Answer answer,User newUser,boolean vote) throws SQLException;
	
	static IAnswerDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBAnswerDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
