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
	
	boolean addQuestion(User newUser,Question question, String forumGroup);
	ArrayList<Question> getAllPosts(User newUser) throws SQLException;
	ArrayList<Question> getAllPosts(String forum_group) throws SQLException;
	static IQuestionDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBQuestionDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
