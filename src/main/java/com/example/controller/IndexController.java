package com.example.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import model.dao.IUserDAO;
import model.dao.IUserDAO.DataSource;
import model.EmailResponse;
import model.User;

@Controller
public class IndexController {
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String indexController(HttpServletRequest req) {
		if(req.getSession().getAttribute("loggedUser") != null)
			return "main";
		return "index";
	}
	
	@RequestMapping(value="/main.htm", method = RequestMethod.GET)
	public String goToMain() {
		return "main";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, Model model){ 
		User loggedUser = null;
		try {
			System.out.println(req.getParameter("email"));
			loggedUser = IUserDAO.getDAO(DataSource.DB).validateUser(req.getParameter("email"), req.getParameter("password"));
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("invalidUser", "Invalid e-mail or password");
			return "index";  
		}
		if (loggedUser != null){
			req.getSession().setAttribute("loggedUser", loggedUser);
			return "main";
		}
		model.addAttribute("invalidUser", "Invalid e-mail or password");
		return "index";  
	}
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest req, Model model){
		String errMsg = validateRegistration(req, model);
		if(errMsg.equals("")){
			User newUser = createUser(req);
			try {
				IUserDAO.getDAO(DataSource.DB).addUser(newUser);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error addin newly registrated user");
				return "errorPage";
			}
			String text = "Hello " + req.getParameter("firstName").toString() + 
					" !<br>You are successfully registrated at TalentHub";
			EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Welcome to TalentHub!", text, req);
			req.getSession().setAttribute("loggedUser", newUser);
			return "main";
		}
		else{
			model.addAttribute("errMsg", errMsg);
			setFieldValues(model, req);
			return "index";
		}
	}

	@RequestMapping(value="/forgottenPassword", method = RequestMethod.GET)
	public String resetPassGet(){
		return "forgottenPassword";
	}
	@RequestMapping(value="/passwordReset", method = RequestMethod.POST)
	public String resetPass(HttpServletRequest req, Model model){
		if(!validateUser(req.getParameter("email").toString())){
			char[] newPass = new char[8];
			Random r = new Random();
			for(int i=0; i < newPass.length; i++){
				newPass[i] = (char)(r.nextInt(26) + 'a');
				if(r.nextBoolean())
					newPass[i] -= 32;
			}
			String pass = new String(newPass);
			try {
				IUserDAO.getDAO(DataSource.DB).changeUserPass(req.getParameter("email").toString(), pass);
				EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Password reset", "Hello user,<br>Your new password is: " + pass, req);
				return "redirect:/html/generatedPassword.html";
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in changeUserPass");
				return "errorPage";
			}
				
		}
		model.addAttribute("errMsg", "Invalid E-mail");
		return "forgottenPassword";
			 
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req){
		req.getSession().removeAttribute("loggedUser");
			return "index";
		}

	@RequestMapping(value="/changeProfile", method = RequestMethod.GET)
	public String viewProfile(){
		return "changeProfile";
	}
	@RequestMapping(value="/changeProfile", method = RequestMethod.POST)
	public String changeProfile(HttpServletRequest req, Model model){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		if(!req.getParameter("password").equals("")) {
			String password = req.getParameter("password");
			loggedUser.setPassword(password);
		}
		/*	if(req.getParameter("twitter_account") != null) {
			String twitterAccount = req.getParameter("twitter_account");
			u.setTwitterAccount(twitterAccount);
		}
		if(req.getParameter("github_account") != null) {
			String githubAccount = req.getParameter("github_account");
			u.setGitHubAccount(githubAccount);
		}
		if(req.getParameter("stackoverflow_account") != null) {
			String stackoverflowAccount = req.getParameter("stackoverflow_account");
			u.setStackOverflowAccount(stackoverflowAccount);
		} */
		if(!req.getParameter("photo_url").equals("")) {
			String photo = req.getParameter("photo_url");
			loggedUser.setPhoto(photo);
		}
		try {
			IUserDAO.getDAO(DataSource.DB).updateUser(loggedUser);
			req.getSession().setAttribute("loggedUser", loggedUser);
			ForumController.reloadUsers();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in updateUser");
			return "errorPage";
		}
		return "main";	
	}
	
	@RequestMapping(value="/getProfile", method = RequestMethod.POST)
	public String getProfile(HttpServletRequest req, Model model){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		User user = null;
		try {
			user = IUserDAO.getDAO(DataSource.DB).getUser(req.getParameter("user"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getUser()");
			return "errorPage";
		}
		model.addAttribute("user", user);
		return "profile";
	}
	
	private void setFieldValues(Model model, HttpServletRequest req) {
		model.addAttribute("firstName", req.getParameter("firstName").toString());
		model.addAttribute("lastName", req.getParameter("lastName").toString());
		model.addAttribute("regEmail", req.getParameter("email").toString());
	}

	private String validateRegistration(HttpServletRequest req, Model model) {
		if(req.getParameter("firstName").toString().equals(""))
			return "Please enter your first name";
		if(req.getParameter("lastName").toString().equals(""))
			return "Please enter your last name";
		if(req.getParameter("email").toString().equals(""))	
			return "Please enter your e-mail address";
		else
			try {
				if(!IUserDAO.getDAO(DataSource.DB).validateUser(req.getParameter("email").toString())){
					return "E-mail already in use";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in validateRegistration()");
				return "errorPage";
			}
		if(req.getParameter("email2").toString().equals(""))	
			return "Please confirm your e-mail address";
		if(!req.getParameter("email").toString().equals(req.getParameter("email2").toString()))
			return "E-mail mismatch";
		if(req.getParameter("pass").toString().equals("") || req.getParameter("pass").toString().length() < 8)
			return "Password must be 8 symbols or longer";
		return "";
	}
	
	private User createUser(HttpServletRequest req) {
		String email = req.getParameter("email").toString();
		String firstName = req.getParameter("firstName").toString();
		String lastName = req.getParameter("lastName").toString();
		String password = req.getParameter("pass").toString();
		String gender = req.getParameter("sex").toString();
		Date birthDate =  Date.valueOf(req.getParameter("bDay"));
		return new User(firstName, lastName, email, password, gender, birthDate);
		
	}
	
	private boolean validateUser(String email) {
		try {
			return IUserDAO.getDAO(DataSource.DB).validateUser(email);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println();
			return false;
		}
	}
}
