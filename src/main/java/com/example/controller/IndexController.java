package com.example.controller;

import java.sql.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import model.dao.IUserDAO;
import model.dao.IUserDAO.DataSource;
import model.db.DBManager;
import model.EmailResponse;
import model.User;

@Controller
public class IndexController {
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String sayHello() {
		return "index";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, Model model){
		User u = IUserDAO.getDAO(DataSource.DB).validateUser(req.getParameter("email"), req.getParameter("password"));
		if (u != null){
			req.getSession().setAttribute("loggedUser", u);
			return "main";
		}
		else{
			model.addAttribute("invalidUser", "Invalid e-mail or password");
			return "index";
		}
	}
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest req, Model model){
		String errMsg = validateRegistration(req, model);
		if(errMsg.equals("")){
			User u = createUser(req);
			IUserDAO.getDAO(DataSource.DB).addUser(u);
			String text = "Hello " + req.getParameter("firstName").toString() + 
					" !<br>You are successfully registrated at TalentHub";
			EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Welcome to TalentHub!", text, req);
			req.getSession().setAttribute("loggedUser", u);
			return "main";
		}
		else{
			model.addAttribute("errMsg", errMsg);
			setFieldValues(model, req);
			return "index";
		}
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
			if(IUserDAO.getDAO(DataSource.DB).changeUserPass(req.getParameter("email").toString(), pass))
				EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Password reset", "Hello user,<br>Your new password is: " + pass, req);
			return "redirect:/html/generatedPassword.html";
		}
		model.addAttribute("errMsg", "Invalid E-mail");
		return "forgottenPassword";
			 
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
		else if(!IUserDAO.getDAO(DataSource.DB).validateUser(req.getParameter("email").toString())){
			return "E-mail already in use";
		}
		if(req.getParameter("email2").toString().equals(""))	
			return "Please confirm your e-mail address";
		if(!req.getParameter("email").toString().equals(req.getParameter("email2").toString()))
			return "E-mail mismatch";
		if(req.getParameter("pass").toString().equals("") || req.getParameter("pass").toString().length() < 8)
			return "Password must be 8 symbols or longer";
		return "";
	}
	
	private boolean validateUser(String email) {
		return IUserDAO.getDAO(DataSource.DB).validateUser(email);
	}
}
