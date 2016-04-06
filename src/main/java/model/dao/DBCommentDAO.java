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
		String query = "SELECT comment_id,user_email,comment_text,date_created FROM talenthub.Comments WHERE post_id=?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setInt(1, post.getPost_id());

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Comment c = new Comment(rs.getInt(1), rs.getString(2), rs.getString(3));
			c.setDate_created(rs.getDate(4));
			commentsByUser.add(c);
		}
		st.close();
		return commentsByUser;
	}
	
}
