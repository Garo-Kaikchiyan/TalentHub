package model.dao;
import java.sql.SQLException;
import java.util.List;

import model.User;
import model.Post;
import model.Group;
import model.dao.IUserDAO.DataSource;
public interface IGroupDAO {

	enum DataSource {
		DB, JSON, XML, CSV, PLC
	}
	
	boolean addGroup(User newUser,Group group);
	List<Post> getAllPosts(Group group) throws SQLException;
	
	
	static IGroupDAO getDAO(DataSource ds) {
		switch (ds) {
		case DB:
			return DBGroupDAO.getInstance();
		default:
			throw new IllegalArgumentException();
		}
	}
	
	boolean addPost(User user,Group group,Post post);
	
	
}
