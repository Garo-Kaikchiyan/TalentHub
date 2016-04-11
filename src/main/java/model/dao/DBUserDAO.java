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
	
	public static DBUserDAO getInstance(){
		if(instance == null)
			instance = new DBUserDAO();
		return instance;
	}
	@Override
	public boolean addUser(User newUser) {
		boolean success = true;
		String query = "INSERT INTO talenthub.users (user_email, user_password, first_name, last_name, gender, birth_date, user_photo) VALUES (?, SHA1(?), ?, ?, ?, ?, ?);";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, newUser.getEmail());
			st.setString(2, newUser.getPassword());
			st.setString(3, newUser.getFirstName());
			st.setString(4, newUser.getLastName());
			st.setString(5, newUser.getGender());
			st.setDate(6, newUser.getBirth());
			st.setString(7, newUser.getPhoto());
			st.execute();
			} catch (SQLException e) {
			success = false;
		}
		return success;
	}

	@Override
	public List<User> getAllUsers() throws SQLException{
		String query = "SELECT first_name, last_name, email, SHA1(password), gender, birth_date, user_photo FROM users;";
		List<User> users = new ArrayList<>();
		Statement st = manager.getConnection().createStatement();
		ResultSet result = st.executeQuery(query);
		System.out.println("result = " + result);
		if(result == null){
			return users;
		}
		while(result.next()){
			System.out.println("row taken");
			User u = new User(result.getString(1),
							  result.getString(2),
					          result.getString(3),
					          result.getString(4),
					          result.getString(5),
					          result.getDate(6));
			u.setPhoto(result.getString(7));
			users.add(u);
		}
		st.close();
		System.out.println(users.size());
		return users;
	}

	@Override
	public boolean updateUser(User loggedUser) {
		String query = "UPDATE USERS SET user_password = SHA1(?), first_name = ?, last_name = ?, gender = ?, birth_date = ?, user_photo = ? WHERE user_email = ?;";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, loggedUser.getPassword());
			st.setString(2, loggedUser.getFirstName());
			st.setString(3, loggedUser.getLastName());
			st.setString(4, loggedUser.getGender());
			st.setDate(5, loggedUser.getBirth());
			st.setString(6, loggedUser.getPhoto());
			st.setString(7, loggedUser.getEmail());
			st.executeUpdate();
			return true;
			} catch (SQLException e) {
				System.out.println("failed update");
				return false;
		}
	}

	@Override
	public User validateUser(String email, String pass) {
		String query = "SELECT first_name, last_name, user_email, gender, birth_date, user_photo  FROM users WHERE user_password =SHA1('" + pass + "');";
		Statement st;
		try {
			st = manager.getConnection().createStatement();
			ResultSet result = st.executeQuery(query);
			if(result == null){
				System.out.println("null user");
				return null;
			}
			else{
				result.next();
				if(result.getString(3).equals(email)){
					User u = new User(result.getString(1),
							  result.getString(2),
					          result.getString(3),
					          pass,
					          result.getString(4),
					          result.getDate(5));
					u.setPhoto(result.getString(6));
					return u;
				}
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error @ validateUser(String, String) ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean validateUser(String email) {
		String query = "SELECT user_email FROM users WHERE user_email ='" + email + "';";
		Statement st;
		try {
			st = manager.getConnection().createStatement();
			ResultSet result = st.executeQuery(query);
			return !result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error @ validateUser(String) ");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changeUserPass(String email, String pass) {
		String query = "UPDATE USERS SET user_password = SHA1(?) WHERE user_email = ?;";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, pass);
			st.setString(2, email);
			st.execute();
			return true;
			} catch (SQLException e) {
				System.out.println("failed update");
				return false;
		}
	}

	@Override
	public User getUser(String email) {
		String query = "SELECT first_name, last_name, user_email, gender, birth_date, user_photo FROM users WHERE user_email = ?;";
		PreparedStatement st;
		try {
			st = manager.getConnection().prepareStatement(query);
			st.setString(1, email);
			ResultSet result = st.executeQuery();
			while(result.next()){
				System.out.println("row taken");
				User u = new User(result.getString(1),
								  result.getString(2),
						          result.getString(3),
						          "",
						          result.getString(4),
						          result.getDate(5));
				u.setPhoto(result.getString(6));
				return u;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public int calculateAllPosts(User newUser) throws SQLException{
		String query="SELECT question_title FROM talenthub.Questions q WHERE q.user_email=?;";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs=st.executeQuery();
		int allForumEntrys=0;
		while(rs.next()){
			allForumEntrys++;
		}
		query="SELECT answer_id FROM talenthub.Answers a WHERE a.user_email=?";
		st=manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		rs=st.executeQuery();
		while(rs.next()){
			allForumEntrys++;
		}
		newUser.setAllForumEntrys(allForumEntrys);
		return allForumEntrys;
	}

	@Override
	public void getLikesFromForumGroupPhp(User newUser) throws SQLException {
		String query="SELECT v.votes FROM answers a,questions q WHERE a.user_email=? AND a.question_title="
				+ "(SELECT question_title FROM questions q WHERE forum_group=phpGroup);";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs=st.executeQuery();
		int likes=0;
		while(rs.next()){
			likes+=rs.getInt(1);
		}
		newUser.setPhpLikes(likes);
	}
	
}
