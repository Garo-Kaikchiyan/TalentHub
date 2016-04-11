package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Question;
import model.Answer;
import model.User;
import model.dao.IQuestionDAO.DataSource;

public interface IAnswerDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}
	
	public void addAnswer(Answer answer,Question question,User newUser) throws SQLException;;
	public ArrayList<Answer> getAllAnswers(Question question) throws SQLException;
	public ArrayList<Answer> getAllAnswer(User newUser) throws SQLException;
	
	public void vote(Answer answer,User newUser,boolean vote) throws SQLException;
	
	static IAnswerDAO getDAO(DataSource db) {
		switch (db) {
		case DB:
			return DBAnswerDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
}
