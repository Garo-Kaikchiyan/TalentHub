package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Group;
import model.Post;
import model.User;
import model.dao.IGroupDAO.DataSource;

public interface IPostDAO {
	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}
	
	boolean addPost(User newUser,Group group,Post post);
	ArrayList<Post> getAllPosts(Group group) throws SQLException;
	ArrayList<Post> getAllPosts(User newUser) throws SQLException;
	
	static IPostDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBPostDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
