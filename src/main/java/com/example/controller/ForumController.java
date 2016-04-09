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
import model.dao.IQuestionDAO;
import model.dao.IQuestionDAO.DataSource;

@Controller
public class ForumController {
	private ArrayList<Question> allJavaQuestions;
	private ArrayList<Question> allPhpQuestions;
	private ArrayList<Question> allAndroidQuestions;
	private ArrayList<Question> allJSQuestions;
	
	@RequestMapping(value="/forum", method = RequestMethod.GET) 
	public String goToForums() {
		return "forum_types";
	}
	
	@RequestMapping(value="/javaForum", method = RequestMethod.GET) 
	public String goToJavaForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("java");
			} catch (SQLException e) {
				System.out.println("Problem retrieving java forum questions");
				e.printStackTrace();
			}
		req.getSession().setAttribute("questions", allJavaQuestions);
		req.getSession().setAttribute("forum", "javaForum");
		return "forum";
	}
	
	@RequestMapping(value="/jsForum", method = RequestMethod.GET) 
	public String goToJSForums(HttpServletRequest req, Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("js");
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
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("android");
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

		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("php");
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
		Question q = new Question(req.getParameter("subject"), u.getEmail(), req.getParameter("text"));
		IQuestionDAO.getDAO(DataSource.DB).addQuestion(u, q);
		String forum = req.getSession().getAttribute("forum").toString();
		addQuestionToList(forum, q);
		return forum;
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
