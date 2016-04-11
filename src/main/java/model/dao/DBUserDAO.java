package model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.db.DBManager;

class DBUserDAO implements IUserDAO {

	private static DBUserDAO instance;
	private DBManager manager;

	private DBUserDAO() {
		manager = DBManager.getInstance();
		System.out.println("db user dao init");
	}

	public static DBUserDAO getInstance() {
		if (instance == null)
			instance = new DBUserDAO();
		return instance;
	}

	@Override
	public void addUser(User newUser) throws SQLException{
		String query = "INSERT INTO talenthub.users (user_email, user_password, first_name, "
				+ "last_name, gender, birth_date, user_photo) VALUES (?, SHA1(?), ?, ?, ?, ?, ?);";
		
		PreparedStatement st = manager.getConnection().prepareStatement(query);
			st.setString(1, newUser.getEmail());
			st.setString(2, newUser.getPassword());
			st.setString(3, newUser.getFirstName());
			st.setString(4, newUser.getLastName());
			st.setString(5, newUser.getGender());
			st.setDate(6, newUser.getBirth());
			st.setString(7, newUser.getPhoto());
			st.execute();
			st.close();
	}

	@Override
	public ArrayList<User> getAllUsers() throws SQLException {
		String query = "SELECT first_name, last_name, user_email, user_password, gender, birth_date, user_photo FROM users;";
		ArrayList<User> users = new ArrayList<>();
		Statement st = manager.getConnection().createStatement();
		ResultSet result = st.executeQuery(query);
		if (result == null) {
			return users;
		}
		while (result.next()) {
			System.out.println("row taken");
			User user = new User(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getDate(6));
			user.setPhoto(result.getString(7));
			users.add(user);
		}
		st.close();
		return users;
	}

	@Override
	public void updateUser(User loggedUser) throws SQLException {
		String query = "UPDATE USERS SET user_password = SHA1(?), first_name = ?, "
				+ "last_name = ?, gender = ?, birth_date = ?, user_photo = ? WHERE user_email = ?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
			st.setString(1, loggedUser.getPassword());
			st.setString(2, loggedUser.getFirstName());
			st.setString(3, loggedUser.getLastName());
			st.setString(4, loggedUser.getGender());
			st.setDate(5, loggedUser.getBirth());
			st.setString(6, loggedUser.getPhoto());
			st.setString(7, loggedUser.getEmail());
			st.executeUpdate();
			st.close();
		}

	@Override
	public User validateUser(String userEmail, String userPassword) throws SQLException {
		String query = "SELECT first_name, last_name, user_email, gender, birth_date, user_photo  FROM users WHERE user_password =SHA1(?);";
		PreparedStatement st;
		st = manager.getConnection().prepareStatement(query);
		st.setString(1, userPassword);
		ResultSet result = st.executeQuery(query);
		if (result.next()) {
			if (result.getString(3).equals(userEmail)) {
				User user = new User(result.getString(1), result.getString(2), result.getString(3), userPassword,
						result.getString(4), result.getDate(5));
				user.setPhoto(result.getString(6));
				return user;
			}
		}
		st.close();
		return null;
	}

	@Override
	public boolean validateUser(String userEmail) throws SQLException {
		String query = "SELECT user_email FROM users WHERE user_email =?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, userEmail);
			ResultSet result = st.executeQuery(query);
			st.close();
			return !result.next();
	}

	@Override
	public void changeUserPass(String userEmail, String userPass) throws SQLException {
		String query = "UPDATE USERS SET user_password = SHA1(?) WHERE user_email = ?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
			st.setString(1, userPass);
			st.setString(2, userEmail);
			st.execute();
			st.close();
	}

	@Override
	public User getUser(String userEmail) throws SQLException {
		String query = "SELECT first_name, last_name, user_email, gender, birth_date, user_photo FROM users WHERE user_email = ?;";
		PreparedStatement st;
			st = manager.getConnection().prepareStatement(query);
			st.setString(1, userEmail);
			ResultSet result = st.executeQuery();
			while (result.next()) {
				System.out.println("row taken");
				User user = new User(result.getString(1), result.getString(2), result.getString(3), "",
						result.getString(4), result.getDate(5));
				user.setPhoto(result.getString(6));
				st.close();
				return user;
			}
			st.close();
			return null;
	}

	
		/**
		 * Calculating the number of forum entries(excluding all entries "Groups" part of the site)
		 */
	@Override
	public int calculateAllPosts(User user) throws SQLException {
		String query = "SELECT question_title FROM talenthub.Questions q WHERE q.user_email=?;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		int allForumEntrys = 0;
		while (rs.next()) {
			allForumEntrys++;
		}
		query = "SELECT answer_id FROM talenthub.Answers a WHERE a.user_email=?";
		st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		rs = st.executeQuery();
		while (rs.next()) {
			allForumEntrys++;
		}
		user.setAllForumEntrys(allForumEntrys);
		st.close();
		return allForumEntrys;
	}

	@Override
	public void getLikesFromForumGroupPhp(User user) throws SQLException {
		String query = "SELECT v.votes FROM answers a,questions q WHERE a.user_email=? AND a.question_title="
				+ "(SELECT question_title FROM questions q WHERE forum_group=phpForum);";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		int likes = 0;
		while (rs.next()) {
			likes += rs.getInt(1);
		}
		user.setPhpLikes(likes);
		st.close();
	}

	@Override
	public void getLikesFromForumGroupJs(User user) throws SQLException {
		String query = "SELECT v.votes FROM answers a,questions q WHERE a.user_email=? AND a.question_title="
				+ "(SELECT question_title FROM questions q WHERE forum_group=jsForum);";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		int likes = 0;
		while (rs.next()) {
			likes += rs.getInt(1);
		}
		user.setJsAnswers(likes);
		st.close();
	}

	@Override
	public void getLikesFromForumGroupAndroid(User user) throws SQLException {
		String query = "SELECT v.votes FROM answers a,questions q WHERE a.user_email=? AND a.question_title="
				+ "(SELECT question_title FROM questions q WHERE forum_group=androidForum);";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		int likes = 0;
		while (rs.next()) {
			likes += rs.getInt(1);
		}
		user.setAndroidLikes(likes);
		st.close();
	}

	@Override
	public void getLikesFromForumGroupEnterprise(User user) throws SQLException {
		String query = "SELECT v.votes FROM answers a,questions q WHERE a.user_email=? AND a.question_title="
				+ "(SELECT question_title FROM questions q WHERE forum_group=javaForum);";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		int likes = 0;
		while (rs.next()) {
			likes += rs.getInt(1);
		}
		user.setJavaLikes(likes);
		st.close();
	}

}
