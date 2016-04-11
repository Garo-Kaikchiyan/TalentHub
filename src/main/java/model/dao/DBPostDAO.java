package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Group;
import model.Post;
import model.User;
import model.db.DBManager;

public class DBPostDAO implements IPostDAO {
	private static DBPostDAO instance;
	private DBManager manager;

	private DBPostDAO() {
		manager = DBManager.getInstance();
		System.out.println("db Post dao init");
	}

	public static DBPostDAO getInstance() {
		if (instance == null)
			instance = new DBPostDAO();
		return instance;
	}

	@Override
	public boolean addPost(User newUser, Group group, Post post) {
		boolean success = true;
		String query = "INSERT INTO talenthub.Posts (user_email,group_name, post,date_created) VALUES(?,?,?,NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, newUser.getEmail());
			st.setString(2, group.getGroup_name());
			st.setString(3, post.getPost());
			st.executeUpdate();
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}

	@Override
	public ArrayList<Post> getAllPosts(Group group) throws SQLException {
		ArrayList<Post> postsByGroup = new ArrayList<>();
		String query = "SELECT p.post_id, p.date_created, p.user_email, p.post, u.first_name,"
				+ "u.last_name,u.birth_date,u.gender, u.user_photo, u.php_answers, u.js_answers, u.android_answers, u.ee_answers "
				+ "FROM talenthub.Posts p, talenthub.Users u WHERE group_name=? AND u.user_email=p.user_email;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, group.getGroup_name());

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Post p = new Post(rs.getString(3), rs.getString(4));
			p.setDate_created(rs.getDate(2));
			p.setPost_id(rs.getInt(1));
			User u = new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_email"), "",
					rs.getString("gender"), rs.getDate("birth_date"));
			u.setPhoto(rs.getString("user_photo"));
			u.setPhpAnswers(rs.getInt("php_answers"));
			u.setJsAnswers(rs.getInt("js_answers"));
			u.setAndroidAnswers(rs.getInt("android_answers"));
			u.setEeAnswers(rs.getInt("ee_answers"));
			p.setOwner(u);
			postsByGroup.add(p);
		}
		st.close();
		return postsByGroup;
	}

	@Override
	public ArrayList<Post> getAllPosts(User newUser) throws SQLException {
		ArrayList<Post> postsByUser = new ArrayList<>();
		String query = "SELECT post_id,user_email, post FROM talenthub.Posts WHERE user_email=?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Post p = new Post(rs.getString(2), rs.getString(3));
			p.setDate_created(rs.getDate(1));
			postsByUser.add(p);
		}
		st.close();
		return postsByUser;
	}

}
