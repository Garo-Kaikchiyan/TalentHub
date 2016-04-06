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
		String query = "INSERT INTO talenthub.users (user_email, user_password, first_name, last_name, gender, birth_date) VALUES (?, SHA1(?), ?, ?, ?, ?);";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, newUser.getEmail());
			st.setString(2, newUser.getPassword());
			st.setString(3, newUser.getFirstName());
			st.setString(4, newUser.getLastName());
			st.setString(5, newUser.getGender());
			st.setDate(6, newUser.getBirth());
			st.execute();
			} catch (SQLException e) {
			success = false;
		}
		return success;
	}

	@Override
	public List<User> getAllUsers() throws SQLException{
		String query = "SELECT first_name, last_name, email, SHA1(password), gender, birth_date FROM users;";
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
			System.out.println("user added");
			users.add(u);
		}
		st.close();
		System.out.println(users.size());
		return users;
	}

	@Override
	public void updateUser(User loggedUser) {
		String query = "UPDATE USERS SET password = ?, first_name = ?, last_name = ? WHERE username = ?;";
		try(PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, loggedUser.getPassword());
			st.setString(2, loggedUser.getFirstName());
			st.setString(3, loggedUser.getLastName());
			st.setString(4, loggedUser.getEmail());
			st.execute();
			} catch (SQLException e) {
				System.out.println("failed update");
		}
	}

}
