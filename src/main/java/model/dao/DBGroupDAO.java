package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Group;
import model.User;
import model.Post;
import model.Question;
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
		String query = "INSERT INTO talentHub.Groups ( group_name,date_created) VALUES (?, NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query);) {
			st.setString(1, group.getGroup_name());
			
			st.execute();
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}
	
//	@Override
//	public ArrayList<Group> getAllGroups() throws SQLException {
//		ArrayList<Group> allGroups = new ArrayList<>();
//		String query= "SELECT group_name, group_message, FROM talentHub.Groups ";
//		PreparedStatement st=manager.getConnection().prepareStatement(query);
//		st.setString(1, forum_group);
//		ResultSet rs=st.executeQuery();
//		while(rs.next()){
//			Question q=new Question(rs.getString(1),rs.getString(2),rs.getString(5),rs.getString(3), rs.getString(4));
//			q.setDate_created(rs.getDate(6));
//			questionsFromGroup.add(q);
//		}
//		st.close();
//		return questionsFromGroup;
//	}

}
