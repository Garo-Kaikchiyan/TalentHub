package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Answer;
import model.Question;
import model.User;
import model.dao.IAnswerDAO;
import model.dao.IQuestionDAO;
import model.dao.IQuestionDAO.DataSource;
import model.dao.IUserDAO;

@Controller
public class ForumController {
	private static ArrayList<Question> allJavaQuestions = new ArrayList<>();
	private static ArrayList<Question> allPhpQuestions = new ArrayList<>();
	private static ArrayList<Question> allAndroidQuestions = new ArrayList<>();
	private static ArrayList<Question> allJSQuestions = new ArrayList<>();
	private static ArrayList<User> allUsers = new ArrayList();
	
	@RequestMapping(value="/forum", method = RequestMethod.GET) 
	public String goToForums() {
		return "forum_types";
	}
	
	@RequestMapping(value="/javaForum", method = RequestMethod.GET) 
	public String goToJavaForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions.isEmpty())
			try {
				allJavaQuestions.addAll(IQuestionDAO.getDAO(DataSource.DB).getAllQuestions("javaForum"));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problem retrieving java forum questions");
				return "errorPage";
			}
		model.addAttribute("endIndex", allJavaQuestions.size());
		req.getSession().setAttribute("questions", allJavaQuestions);
		req.getSession().setAttribute("forum", "javaForum");
		return "forum";
	}
	
	@RequestMapping(value="/jsForum", method = RequestMethod.GET) 
	public String goToJSForums(HttpServletRequest req, Model model) {
		if(allJSQuestions.isEmpty())
			try {
				allJSQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllQuestions("jsForum");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problem retrieving js forum questions");
				return "errorPage";
			}
		model.addAttribute("endIndex", allJSQuestions.size());
		req.getSession().setAttribute("questions", allJSQuestions);
		req.getSession().setAttribute("forum", "jsForum");
		return "forum";
	}
	
	@RequestMapping(value="/androidForum", method = RequestMethod.GET) 
	public String goToAndroidForums(HttpServletRequest req, Model model) {
		if(allAndroidQuestions.isEmpty())
			try {
				allAndroidQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllQuestions("androidForum");
				Collections.sort(allAndroidQuestions, new Comparator<Question>() {

					@Override
					public int compare(Question o1, Question o2) {
						if(o1.getDate_created().compareTo(o2.getDate_created()) > 0) {
							return 1;
						} else {
							return -1;
						}
					}});;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problem retrieving android forum questions");
				return "errorPage";
			}
		model.addAttribute("endIndex", allAndroidQuestions.size());
		req.getSession().setAttribute("questions", allAndroidQuestions);
		req.getSession().setAttribute("forum", "androidForum");
		return "forum";
	}
	
	@RequestMapping(value="/phpForum", method = RequestMethod.GET) 
	public String goToPhpForums(HttpServletRequest req, Model model) {

		if(allPhpQuestions.isEmpty())
			try {
				allPhpQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllQuestions("phpForum");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problem retrieving php forum questions");
				return "errorPage";
			}
		model.addAttribute("endIndex", allPhpQuestions.size());
		req.getSession().setAttribute("questions", allPhpQuestions);
		req.getSession().setAttribute("forum", "phpForum");
		return "forum";
	}

	@RequestMapping(value="/createQuestion", method = RequestMethod.POST) 
	public String createQuestion(HttpServletRequest req, Model mod){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		User autor = (User) req.getSession().getAttribute("loggedUser");
		Question question = new Question(req.getParameter("subject"), autor.getEmail(), req.getParameter("text"), autor.getFirstName(), autor.getLastName());
		String forum = req.getSession().getAttribute("forum").toString();
		try {
			IQuestionDAO.getDAO(DataSource.DB).addQuestion(autor, question, forum);
			autor.setAllForumEntrys(autor.getAllForumEntrys()+1);
			question.setOwner(getUserByEmail(question.getUser_email()));
			addQuestionToList(forum, question);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error adding question in " + forum);
			return "errorPage";
		}

		mod.addAttribute("answers", question.getAnswers());
		mod.addAttribute("question", question);
		return "forum_look";
	}
	
	@RequestMapping(value="/thread", method = RequestMethod.GET)
	public String viewQuestion(HttpServletRequest req, Model mod){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		Question question = null;
		switch(req.getSession().getAttribute("forum").toString()){
		case "javaForum":
			question = allJavaQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			mod.addAttribute("forumName", "Java Forum");
			break;
		case "androidForum":
			question = allAndroidQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			mod.addAttribute("forumName", "Android Forum");
			break;
		case "phpForum":
			question = allPhpQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			mod.addAttribute("forumName", "PHP Forum");
			break;
		case "jsForum":
			question = allJSQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			mod.addAttribute("forumName", "JavaScript Forum");
			break;
		}
			try {
				question.setAnswers(IAnswerDAO.getDAO(model.dao.IAnswerDAO.DataSource.DB).getAllAnswers(question));
			} catch (SQLException e) {
				System.out.println("Problem getting answers");
				e.printStackTrace();
				return "errorPage";
			}
		question.setOwner(getUserByEmail(question.getUser_email()));
		mod.addAttribute("answers", question.getAnswers());
		mod.addAttribute("question", question);
		return "forum_look";
	}

	@RequestMapping(value="/createTopic", method = RequestMethod.GET) 
	public String createTopic(Model model, HttpServletRequest req) {
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		return "create_topic";
	}
	
	@RequestMapping(value="/addAnswer", method = RequestMethod.POST)
	public String addAnswer(HttpServletRequest req, Model mod){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		User user = (User) req.getSession().getAttribute("loggedUser");
		Question question = (Question) req.getSession().getAttribute("question");
		Answer answer = new Answer(question.getQuestion_title(), user.getEmail(), req.getParameter("text"));
		try {
			IAnswerDAO.getDAO(model.dao.IAnswerDAO.DataSource.DB).addAnswer(answer, question, user);
			user.setAllForumEntrys(user.getAllForumEntrys()+1);
			answer.setOwner(getUserByEmail(answer.getUser_email()));
			question.addAnswer(answer);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error adding answer");
			return "errorPage";
		}
		mod.addAttribute("question", question);
		mod.addAttribute("answers", question.getAnswers());
		return "forum_look";
	}
	
	private void addQuestionToList(String type, Question q){
		switch(type){
		case "javaForum":
			allJavaQuestions.add(q);
			break;
		case "androidForum":
			allAndroidQuestions.add(q);
			break;
		case "phpForum":
			allPhpQuestions.add(q);
			break;
		case "jsForum":
			allJSQuestions.add(q);
			break;
			
		}
	}
	
	public static User getUserByEmail(String email){
		if(allUsers.isEmpty()){
			try {
				allUsers.addAll(IUserDAO.getDAO(model.dao.IUserDAO.DataSource.DB).getAllUsers());
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error with getAllUsers()");
				return null;
			}
		}
		for(int i = 0; i < allUsers.size(); i++)
			if(allUsers.get(i).getEmail().equals(email))
				return allUsers.get(i);
		return null;
	}
	
	public static void reloadUsers(){
			try {
				allUsers.clear();
				allUsers.addAll(IUserDAO.getDAO(model.dao.IUserDAO.DataSource.DB).getAllUsers());
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error with setUsers()");
			}
	}
	

}
