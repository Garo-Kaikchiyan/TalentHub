package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Answer;
import model.Question;
import model.User;
import model.db.DBManager;

public class DBAnswerDAO	implements IAnswerDAO {
	private static DBAnswerDAO instance;
	private DBManager manager;
	
	private DBAnswerDAO(){
		manager=DBManager.getInstance();
		System.out.println("answer dao init");
	}
	
	public static DBAnswerDAO getInstance(){
		if(instance==null)
			instance=new DBAnswerDAO();
		return instance;
	}
	
	@Override
	public boolean addAnswer(Answer answer, Question question, User newUser) {
		boolean success=true;
		String query="INSERT INTO talenthub.Answers (user_email,question_title,answer_text,date_created) VALUES(?,?,?,NOW());";
		try(PreparedStatement st=manager.getConnection().prepareStatement(query)){
			st.setString(1, newUser.getEmail());
			st.setString(2, question.getQuestion_title());
			st.setString(3,answer.getText());
			st.execute();
		} catch (SQLException e) {
			success=false;
		}
		
		return success;
	}

	@Override
	public ArrayList<Answer> getAllAnswers(Question question) throws SQLException {
		ArrayList<Answer> answersForQuestion = new ArrayList<>();
		String query="SELECT user_email,answer_text,date_created,likes FROM talenthub.Answers WHERE question_title=?";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, question.getQuestion_title());
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			Answer a=new Answer(question.getQuestion_title(),rs.getString(1),rs.getString(2));
			a.setDate_created(rs.getDate(3));
			a.setLikes(rs.getInt(4));
			answersForQuestion.add(a);
		}
		st.close();
		return answersForQuestion;
	}

	@Override
	public ArrayList<Answer> getAllPosts(User newUser) throws SQLException {
		ArrayList<Answer> answersForUser = new ArrayList<>();
		String query="SELECT question_title,answer_text,date_created,likes FROM talenthub.Answers WHERE user_email=?";
		PreparedStatement st=manager.getConnection().prepareStatement(query);
		st.setString(1, newUser.getEmail());
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			Answer a=new Answer(rs.getString(1),newUser.getEmail(),rs.getString(2));
			a.setDate_created(rs.getDate(3));
			a.setLikes(rs.getInt(4));
			answersForUser.add(a);
		}
		st.close();
		return answersForUser;	}

}
