package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.User;
import model.Comment;
import model.Post;


public interface ICommentDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}

	boolean addComment(Post post,Comment comment,User newUser);

	List<Comment> getAllComments(User user) throws SQLException;
	List<Comment> getAllComments(Post post) throws SQLException;
	

	static ICommentDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBCommentDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
}
