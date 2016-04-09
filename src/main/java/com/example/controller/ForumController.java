package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Question;
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
	public String goToJavaForums(Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("java");
			} catch (SQLException e) {
				System.out.println("Problem retrieving java forum questions");
				e.printStackTrace();
			}
		model.addAttribute("questions", allJavaQuestions);
		model.addAttribute("startIndex", 0);
		if(allJavaQuestions != null){
			if(allJavaQuestions.size()-1 >= 20)
				model.addAttribute("endIndex", 20);
			else
				model.addAttribute("endIndex", allJavaQuestions.size() - 1);
		}
		return "forum";
	}
	
	@RequestMapping(value="/jsForum", method = RequestMethod.GET) 
	public String goToJSForums(Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("js");
			} catch (SQLException e) {
				System.out.println("Problem retrieving js forum questions");
				e.printStackTrace();
			}
		if(allJavaQuestions != null){
			model.addAttribute("questions", allJSQuestions);
			model.addAttribute("startIndex", 0);
			if(allJavaQuestions.size()-1 >= 20)
				model.addAttribute("endIndex", 20);
			else
				model.addAttribute("endIndex", allJSQuestions.size() - 1);
		}
		return "forum";
	}
	
	@RequestMapping(value="/androidForum", method = RequestMethod.GET) 
	public String goToAndroidForums(Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("android");
			} catch (SQLException e) {
				System.out.println("Problem retrieving android forum questions");
				e.printStackTrace();
			}
		if(allJavaQuestions != null){
			model.addAttribute("questions", allAndroidQuestions);
			model.addAttribute("startIndex", 0);
			if(allJavaQuestions.size()-1 >= 20)
				model.addAttribute("endIndex", 20);
			else
				model.addAttribute("endIndex", allAndroidQuestions.size() - 1);
		}
		return "forum";
	}
	
	@RequestMapping(value="/phpForum", method = RequestMethod.GET) 
	public String goToPhpForums(Model model) {
		if(allJavaQuestions == null)
			try {
				allJavaQuestions = IQuestionDAO.getDAO(DataSource.DB).getAllPosts("php");
			} catch (SQLException e) {
				System.out.println("Problem retrieving php forum questions");
				e.printStackTrace();
			}
		if(allJavaQuestions != null){
			model.addAttribute("questions", allPhpQuestions);
			model.addAttribute("startIndex", 0);
			if(allJavaQuestions.size()-1 >= 20)
				model.addAttribute("endIndex", 20);
			else
				model.addAttribute("endIndex", allPhpQuestions.size() - 1);
		}
		return "forum";
	}
	
	

}
