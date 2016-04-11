package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Comment;
import model.Post;
import model.User;
import model.db.DBManager;

public class DBCommentDAO implements ICommentDAO {
	private static DBCommentDAO instance;
	private DBManager manager;

	private DBCommentDAO() {
		manager = DBManager.getInstance();
		System.out.println("db Comment dao init");
	}
	
	public static DBCommentDAO getInstance() {
		if (instance == null)
			instance = new DBCommentDAO();
		return instance;
	}

	@Override
	public boolean addComment(Post post, Comment comment, User newUser) {
		boolean success = true;
		String query = "INSERT INTO talenthub.Comments (user_email,post_id,comment_text,date_created) VALUES (?,?,?,NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, newUser.getEmail());
			st.setInt(2, post.getPost_id());
			st.setString(3, comment.getText());
			st.execute();
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}

	@Override
	public List<Comment> getAllComments(User newUser) throws SQLException {
		ArrayList<Comment> commentsByUser = new ArrayList<>();
		String query = "SELECT comment_id,comment_text,date_created FROM talenthub.Comments WHERE user_email=?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Comment c = new Comment(rs.getInt(1), newUser.getEmail(), rs.getString(2));
			c.setDate_created(rs.getDate(3));
			commentsByUser.add(c);
		}
		st.close();
		return commentsByUser;
	}

	@Override
	public List<Comment> getAllComments(Post post) throws SQLException {
		ArrayList<Comment> commentsByUser = new ArrayList<>();
		String query = "SELECT c.comment_id,c.user_email,c.comment_text,c.date_created,u.first_name"
				+ "u.last_name,u.birth_date,u.gender,u.user_photo,u.php_answers,u.js_answers,u.android_answers,u.ee_answers "
						+ " FROM talenthub.Comments c, talenthub.Users u WHERE post_id=? AND u.user_email=p.user_email;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setInt(1, post.getPost_id());

		ResultSet rs = st.executeQuery();
		
		while (rs.next()) {
			Comment c = new Comment(rs.getInt(1), rs.getString(2), rs.getString(3));
			c.setDate_created(rs.getDate(4));
			User u=new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_email"), "", rs.getString("gender"), rs.getDate("birth_date"));
			u.setPhoto(rs.getString("user_photo"));
			u.setPhpAnswers(rs.getInt("php_answers"));
			u.setJsAnswers(rs.getInt("js_answers"));
			u.setAndroidAnswers(rs.getInt("android_answers"));
			u.setEeAnswers(rs.getInt("ee_answers"));
			c.setOwner(u);
			commentsByUser.add(c);
		}
		st.close();
		return commentsByUser;
	}
	
}
