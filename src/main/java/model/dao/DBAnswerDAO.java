package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Answer;
import model.Question;
import model.User;
import model.db.DBManager;

public class DBAnswerDAO implements IAnswerDAO {
	private static DBAnswerDAO instance;
	private DBManager manager;

	private DBAnswerDAO() {
		manager = DBManager.getInstance();
		System.out.println("answer dao init");
	}

	public static DBAnswerDAO getInstance() {
		if (instance == null)
			instance = new DBAnswerDAO();
		return instance;
	}

	@Override
	public boolean addAnswer(Answer answer, Question question, User newUser) {
		boolean success = true;
		String query = "INSERT INTO talenthub.Answers (user_email,question_title,answer_text,date_created) VALUES(?,?,?,NOW());";
		try (PreparedStatement st = manager.getConnection().prepareStatement(query)) {
			st.setString(1, newUser.getEmail());
			st.setString(2, question.getQuestion_title());
			st.setString(3, answer.getText());
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	@Override
	public ArrayList<Answer> getAllAnswers(Question question) throws SQLException {
		ArrayList<Answer> answersForQuestion = new ArrayList<>();
		String query = "SELECT a.answer_id,a.user_email,a.answer_text,a.date_created, u.first_name, u.last_name,u.birth_date,u.gender,u.picture,u.php_answers,u.js_answers,u.android_answers,u.ee_answers FROM talenthub.Answers a, talenthub.Users u WHERE question_title=? AND u.user_email=a.user_email;";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, question.getQuestion_title());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Answer a = new Answer(question.getQuestion_title(), rs.getString(2), rs.getString(3));
			a.setDate_created(rs.getDate(4));
			// a.setLikes(rs.getInt(5));
			a.setAnswer_id(rs.getInt(1));
			a.getOwner().setEmail(rs.getString(2));
			a.getOwner().setFirstName(rs.getString(5));
			a.getOwner().setLastName(rs.getString(6));
			a.getOwner().setBirth(rs.getDate(7));
			a.getOwner().setPhoto(rs.getString(8));
			a.getOwner().setGender(rs.getString(9));
			a.getOwner().setPhpAnswers(rs.getInt(10));
			a.getOwner().setJsAnswers(rs.getInt(11));
			a.getOwner().setAndroidAnswers(rs.getInt(12));
			a.getOwner().setEeAnswers(rs.getInt(13));
			answersForQuestion.add(a);
		}
		st.close();
		return answersForQuestion;
	}

	@Override
	public ArrayList<Answer> getAllPosts(User newUser) throws SQLException {
		ArrayList<Answer> answersForUser = new ArrayList<>();
		String query = "SELECT question_title,answer_text,date_created,likes FROM talenthub.Answers WHERE user_email=?";
		PreparedStatement st = manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Answer a = new Answer(rs.getString(1), newUser.getEmail(), rs.getString(2));
			a.setDate_created(rs.getDate(3));
			a.setLikes(rs.getInt(4));
			answersForUser.add(a);
		}
		st.close();
		return answersForUser;
	}

	public void vote(Answer answer, User newUser, boolean vote) throws SQLException {
		// cheking if the vote has been registered
		String chekup = "SELECT answer_id,user_email FROM talenthub.Votes WHERE answer_id=? AND user_email=?";
		PreparedStatement st = manager.getConnection().prepareStatement(chekup);
		st.setInt(1, answer.getAnswer_id());
		st.setString(2, newUser.getEmail());
		ResultSet rs = st.executeQuery(chekup);
		while (rs.next()) {
			if (rs.getInt(1) == answer.getAnswer_id() && rs.getString(2).equals(newUser.getEmail()))
				throw new SQLException();

		}
		// adding the vote to DB
		String query = "INSERT INTO talenthub.Votes (answer_id,user_email,votes) VALUES (?,?,?);";
		st = manager.getConnection().prepareStatement(query);
		st.setInt(1, answer.getAnswer_id());
		st.setString(2, newUser.getEmail());
		st.setBoolean(3, vote);
		st.executeUpdate();
		// getting the total votes to calculate the new sum
		String calc = "SELECT votes FROM talenthub.Votes WHERE answer_id=?;";
		st = manager.getConnection().prepareStatement(calc);
		st.setInt(1, answer.getAnswer_id());
		rs = st.executeQuery();
		int votes = 0;
		while (rs.next()) {
			if (rs.getBoolean(1))
				votes++;
			else
				votes--;
		}
		answer.setLikes(votes);
		// String update="UPDATE talenthub.Answers SET votes=?;";
		// st=manager.getConnection().prepareStatement(update);
		// st.setInt(1, votes);
		// st.executeUpdate();
	}
}
