package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import model.Comment;
import model.Post;


public interface ICommentDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}

	boolean addComment(Post post,Comment comment,User newUser);

	ArrayList<Comment> getAllComments(User user) throws SQLException;
	ArrayList<Comment> getAllComments(Post post) throws SQLException;
	

	static ICommentDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBCommentDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
}
