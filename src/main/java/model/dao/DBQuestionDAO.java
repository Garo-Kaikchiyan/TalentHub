package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Question;
import model.User;
import model.db.DBManager;

public class DBQuestionDAO implements IQuestionDAO {
	private static DBQuestionDAO instance;
	private DBManager manager;

	private DBQuestionDAO() {
		manager = DBManager.getInstance();
		System.out.println("question dao init");
	}

	public static DBQuestionDAO getInstance() {
		if (instance == null)
			instance = new DBQuestionDAO();
		return instance;
	}

	@Override
	public boolean addQuestion(User newUser, Question question) {
		boolean success = true;
		String query = "INSERT INTO talenthub.Questions (question_title,user_email,question_text,date_created) VALUES (?,?,?,NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query)) {
			st.setString(1, question.getQuestion_title());
			st.setString(2, newUser.getEmail());
			st.setString(3, question.getQuestion_text());
			st.execute();
		} catch (SQLException e) {
			success = false;
		}
		return success;
	}
	
	
	@Override
	public ArrayList<Question> getAllPosts(User newUser) throws SQLException {
		ArrayList<Question> questionsFromUser = new ArrayList<>();
		String query = "SELECT question_title,question_text,date_created FROM talenthub.Questions WHERE user_email=?";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Question q = new Question(rs.getString(1), newUser.getEmail(), rs.getString(2));
			q.setDate_created(rs.getDate(3));
			questionsFromUser.add(q);
		}
		st.close();
		return questionsFromUser;
	}

	@Override
	public ArrayList<Question> getAllPosts(String forum_group) throws SQLException {
		ArrayList<Question> questionsFromGroup=new ArrayList<>();
		String query= "SELECT q.question_title,q.user_email,u.first_name,u.last_name,q.question_text,q.date_created FROM talenthub.Questions q, talenhub.Users u WHERE q.user_email=u.user_email AND q.forum_group=?;";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, forum_group);
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			Question q=new Question(rs.getString(1),rs.getString(2),rs.getString(5));
			q.setUser_name(rs.getString(3));
			q.setUser_family(rs.getString(4));
			q.setDate_created(rs.getDate(6));
		}
		st.close();
		return questionsFromGroup;
	}

}
