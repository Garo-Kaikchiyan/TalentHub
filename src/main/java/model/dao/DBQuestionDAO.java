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
	public boolean addQuestion(User newUser, Question question, String forumGroup) {
		boolean success = true;
		String query = "INSERT INTO talenthub.Questions (question_title, user_email, question_text, date_created, forum_group) VALUES (?,?,?,NOW(), ?);";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query)) {
			st.setString(1, question.getQuestion_title());
			st.setString(2, newUser.getEmail());
			st.setString(3, question.getQuestion_text());
			st.setString(4, forumGroup);
			st.execute();
			System.out.println("Question added");
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	
	@Override
	public ArrayList<Question> getAllQuestions(User newUser) throws SQLException {
		ArrayList<Question> questionsFromUser = new ArrayList<>();
		String query = "SELECT question_title,question_text,date_created FROM talenthub.Questions WHERE user_email=?";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Question q = new Question(rs.getString(1), newUser.getEmail(), rs.getString(2), newUser.getFirstName(), newUser.getLastName());
			q.setDate_created(rs.getDate(3));
			questionsFromUser.add(q);
		}
		st.close();
		return questionsFromUser;
	}

	@Override
	public ArrayList<Question> getAllQuestions(String forum_group) throws SQLException {
		ArrayList<Question> questionsFromGroup=new ArrayList<>();
		String query= "SELECT q.question_title,q.user_email,u.first_name,u.last_name,q.question_text,q.date_created,u.birth_date,u.gender,u.picture,u.php_answers,u.js_answers,u.android_answers,u.ee_answers FROM talenthub.Questions q, talenthub.Users u WHERE q.user_email=u.user_email AND q.forum_group=?;";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, forum_group);
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			Question q=new Question(rs.getString(1),rs.getString(2),rs.getString(5),rs.getString(3), rs.getString(4));
			q.setDate_created(rs.getDate(6));
			q.getOwner().setEmail(rs.getString(1));
			q.getOwner().setFirstName(rs.getString(3));
			q.getOwner().setLastName(rs.getString(4));
			q.getOwner().setBirth(rs.getDate(6));
			q.getOwner().setGender(rs.getString(7));
			q.getOwner().setPhoto(rs.getString(8));
			q.getOwner().setPhpAnswers(rs.getInt(9));
			q.getOwner().setJsAnswers(rs.getInt(10));
			q.getOwner().setAndroidAnswers(rs.getInt(11));
			q.getOwner().setEeAnswers(rs.getInt(12));
			questionsFromGroup.add(q);
		}
		st.close();
		return questionsFromGroup;
	}

}
