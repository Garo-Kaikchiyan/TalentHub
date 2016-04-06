package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Group;
import model.User;
import model.Post;
import model.db.DBManager;

public class DBGroupDAO implements IGroupDAO{
	private static DBGroupDAO instance;
	private DBManager manager;
	
	private DBGroupDAO() {
		manager = DBManager.getInstance();
		System.out.println("db user dao init");
	}
	
	public static DBGroupDAO getInstance(){
		if(instance == null)
			instance = new DBGroupDAO();
		return instance;
	}

	@Override
	public boolean addGroup(User newUser, Group group) {
		boolean success = true;
		String query = "INSERT INTO team_project.Groups ( group_name,date_created) VALUES (?, NOW());";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, group.getGroup_name());
			//date of birth
			st.execute();
			} catch (SQLException e) {
			success = false;
		}
		return success;
		}

	@Override
	public List<Post> getAllPosts(Group group) throws SQLException {
		return null;
	}

	@Override
	public boolean addPost(User user,Group group, Post post) {
		boolean success=true;
		String query="INSERT INTO talenthub.Posts (user_email,group_name,post_title,post) VALUES(?,?,?,?)";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, user.getEmail());
			st.setString(2, group.getGroup_name());
			st.setString(3, post.getPost_title());
			st.setString(4, post.getPost());
			//date of birth
			st.execute();
			} catch (SQLException e) {
			success = false;
		}
		return success;
	}



}
