package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.User;

public interface IUserDAO {

	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}

	static IUserDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBUserDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}


	public void addUser(User newUser) throws SQLException;

	public ArrayList<User> getAllUsers() throws SQLException;
	
	public User getUser(String email) throws SQLException;

	public void updateUser(User loggedUser) throws SQLException;

	public void changeUserPass(String email, String pass) throws SQLException;

	public User validateUser(String email, String pass) throws SQLException;

	public boolean validateUser(String email) throws SQLException;

	public void getLikesFromForumGroupPhp(User newUser) throws SQLException;

	public void getLikesFromForumGroupJs(User newUser) throws SQLException;

	public void getLikesFromForumGroupAndroid(User newUser) throws SQLException;

	public void getLikesFromForumGroupEnterprise(User newUser) throws SQLException;

	public int calculateAllPosts(User newUser) throws SQLException;
}
