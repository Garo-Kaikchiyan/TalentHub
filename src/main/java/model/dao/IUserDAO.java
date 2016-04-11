package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.User;

public interface IUserDAO {

	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}

	boolean addUser(User newUser);

	List<User> getAllUsers() throws SQLException;

	static IUserDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBUserDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}

	User getUser(String email);

	boolean updateUser(User loggedUser);

	boolean changeUserPass(String email, String pass);

	User validateUser(String email, String pass);

	boolean validateUser(String email);

	void getLikesFromForumGroupPhp(User newUser) throws SQLException;

	void getLikesFromForumGroupJs(User newUser) throws SQLException;

	void getLikesFromForumGroupAndroid(User newUser) throws SQLException;

	void getLikesFromForumGroupEnterprise(User newUser) throws SQLException;

	public int calculateAllPosts(User newUser) throws SQLException;
}
