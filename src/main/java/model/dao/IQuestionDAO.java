package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import model.Question;
import model.dao.IPostDAO.DataSource;

public interface IQuestionDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}
	
	void addQuestion(User newUser,Question question, String forumGroup) throws SQLException;
//	ArrayList<Question> getAllQuestions(User user) throws SQLException;
	ArrayList<Question> getAllQuestions(String forum_group) throws SQLException;
	static IQuestionDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBQuestionDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
