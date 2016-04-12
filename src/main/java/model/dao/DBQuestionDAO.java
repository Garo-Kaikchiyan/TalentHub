package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.controller.ForumController;

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
	public void addQuestion(User loggedUser, Question question, String forumGroup) throws SQLException{
		String query = "INSERT INTO talenthub.Questions (question_title, user_email, question_text, date_created, forum_group) VALUES (?,?,?,NOW(), ?);";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
			st.setString(1, question.getQuestion_title());
			st.setString(2, loggedUser.getEmail());
			st.setString(3, question.getQuestion_text());
			st.setString(4, forumGroup);
			st.execute();
			st.close();
	}
	

	
//	To be implemented method that retrieves all the Questions posted from an user;	
//	
//	@Override
//	public ArrayList<Question> getAllQuestions(User user) throws SQLException {
//		ArrayList<Question> questionsFromUser = new ArrayList<>();
//		String query = "SELECT question_title,question_text,date_created FROM talenthub.Questions WHERE user_email=?";
//		PreparedStatement st = manager.getConnection().prepareStatement(query);
//		st.setString(1, user.getEmail());
//		ResultSet rs = st.executeQuery();
//		while (rs.next()) {
//			Question q = new Question(rs.getString(1), user.getEmail(), rs.getString(2), user.getFirstName(), user.getLastName());
//			q.setDate_created(rs.getDate(3));
//			questionsFromUser.add(q);
//		}
//		st.close();
//		return questionsFromUser;
//	}

	@Override
	public ArrayList<Question> getAllQuestions(String forumGroup) throws SQLException {
		ArrayList<Question> questionsFromGroup=new ArrayList<>();
//		String query = "SELECT q.question_title, q.user_email, u.first_name, u.last_name,"
//				+ "q.question_text, q.date_created, u.birth_date, u.gender, u.user_photo,"
//				+ " u.php_answers, u.js_answers, u.android_answers, u.ee_answers FROM talenthub.Questions q,"
//				+ " talenthub.Users u WHERE q.user_email = u.user_email AND q.forum_group = ? ORDER BY q.date_created ASC;";
		
		String query= "SELECT q.question_title, q.user_email, u.first_name, u.last_name, q.question_text, q.date_created "
					+ "FROM talenthub.Questions q, talenthub.Users u WHERE q.user_email=u.user_email AND q.forum_group=? "
					+ "ORDER BY q.date_created ASC;";
		
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		
		st.setString(1, forumGroup);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next()){
			Question question = new Question(rs.getString(1),rs.getString(2),rs.getString(5),rs.getString(3), rs.getString(4));
			question.setDate_created(rs.getDate(6));
//			User u = new User(rs.getString(3), rs.getString(4),rs.getString(2), "", rs.getString(8), rs.getDate(7));
//			u.setPhoto(rs.getString("user_photo"));
//			System.out.println(rs.getString(9));
//			u.setPhpAnswers(rs.getInt(10));
//			u.setJsAnswers(rs.getInt(11));
//			u.setAndroidAnswers(rs.getInt(12));
//			u.setEeAnswers(rs.getInt(13));
//			u.setAllForumEntrys(IUserDAO.getDAO(model.dao.IUserDAO.DataSource.DB).calculateAllPosts(u)); 
			question.setOwner(ForumController.getUserByEmail(question.getUser_email()));
			questionsFromGroup.add(question);
		}
		st.close();
		return questionsFromGroup;
	}

}
