package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Group;
import model.User;
import model.Post;
import model.db.DBManager;

public class DBGroupDAO implements IGroupDAO {
	private static DBGroupDAO instance;
	private DBManager manager;

	private DBGroupDAO() {
		manager = DBManager.getInstance();
		System.out.println("db Group dao init");
	}

	public static DBGroupDAO getInstance() {
		if (instance == null)
			instance = new DBGroupDAO();
		return instance;
	}

	@Override
	public boolean addGroup(User newUser, Group group) {
		boolean success = true;
		String query = "INSERT INTO team_project.Groups ( group_name,date_created) VALUES (?, NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, group.getGroup_name());
			
			st.execute();
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}

}
