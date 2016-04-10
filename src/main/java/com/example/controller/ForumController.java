package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Question;
import model.User;
import model.dao.IAnswerDAO;
import model.dao.IQuestionDAO;
import model.dao.IQuestionDAO.DataSource;

@Controller
public class ForumController {
	private ArrayList<Question> allJavaQuestions = new ArrayList<>();
	private ArrayList<Question> allPhpQuestions = new ArrayList<>();
	private ArrayList<Question> allAndroidQuestions = new ArrayList<>();
	private ArrayList<Question> allJSQuestions = new ArrayList<>();
	
	@RequestMapping(value="/forum", method = RequestMethod.GET) 
	public String goToForums() {
		return "forum_types";
	}
	
	@RequestMapping(value="/javaForum", method = RequestMethod.GET) 
	public String goToJavaForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions.isEmpty())
			try {
				allJavaQuestions.addAll(IQuestionDAO.getDAO(DataSource.DB).getAllPosts("javaForum"));
				System.out.println(allJavaQuestions.size());
			} catch (SQLException e) {
				System.out.println("Problem retrieving java forum questions");
				e.printStackTrace();
			}
		model.addAttribute("endIndex", allJavaQuestions.size());
		req.getSession().setAttribute("questions", allJavaQuestions);
		req.getSession().setAttribute("forum", "javaForum");
		return "forum";
	}
	
	@RequestMapping(value="/jsForum", method = RequestMethod.GET) 
	public String goToJSForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions.isEmpty())
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("jsForum");
			} catch (SQLException e) {
				System.out.println("Problem retrieving js forum questions");
				e.printStackTrace();
			}
		req.getSession().setAttribute("questions", allJSQuestions);
		req.getSession().setAttribute("forum", "jsForum");
		return "forum";
	}
	
	@RequestMapping(value="/androidForum", method = RequestMethod.GET) 
	public String goToAndroidForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("androidForum");
			} catch (SQLException e) {
				System.out.println("Problem retrieving android forum questions");
				e.printStackTrace();
			}
		req.getSession().setAttribute("questions", allAndroidQuestions);
		req.getSession().setAttribute("forum", "androidForum");
		return "forum";
	}
	
	@RequestMapping(value="/phpForum", method = RequestMethod.GET) 
	public String goToPhpForums(HttpServletRequest req, Model model) {

		if(allJavaQuestions.isEmpty())
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("phpForum");
			} catch (SQLException e) {
				System.out.println("Problem retrieving php forum questions");
				e.printStackTrace();
			}
		req.getSession().setAttribute("questions", allPhpQuestions);
		req.getSession().setAttribute("forum", "phpForum");
		return "forum";
	}

	@RequestMapping(value="/createQuestion", method = RequestMethod.POST) 
	public String createQuestion(HttpServletRequest req){
		User u = (User) req.getSession().getAttribute("loggedUser");
		System.out.println(u.getEmail());
		Question q = new Question(req.getParameter("subject"), u.getEmail(), req.getParameter("text"), u.getFirstName(), u.getLastName());
		String forum = req.getSession().getAttribute("forum").toString();
		IQuestionDAO.getDAO(DataSource.DB).addQuestion(u, q, forum);
		addQuestionToList(forum, q);
		return forum;
	}
	
	@RequestMapping(value="/thread", method = RequestMethod.GET)
	public String viewQuestion(HttpServletRequest req, Model mod){
		Question q = null;
		switch(req.getSession().getAttribute("forum").toString()){
		case "javaForum":
			q = allJavaQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			break;
		case "androidForum":
			q = allAndroidQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			break;
		case "phpForum":
			q = allPhpQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			break;
		case "jsForum":
			q = allJSQuestions.get(Integer.parseInt(req.getParameter("questionIndex")));
			break;
		}
		if(q.getAnswers() == null){
			try {
				q.setAnswers(IAnswerDAO.getDAO(model.dao.IAnswerDAO.DataSource.DB).getAllAnswers(q));
			} catch (SQLException e) {
				System.out.println("Problem getting answers");
				e.printStackTrace();
			}
		}
		mod.addAttribute("answers", q.getAnswers());
		mod.addAttribute("question", q);
		return "forum_look";
	}

	@RequestMapping(value="/createTopic", method = RequestMethod.GET) 
	public String createTopic(Model model) {
		return "create_topic";
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
	
	
	

}
