package com.example.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.EmailResponse;

@Controller
public class IndexController {
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String sayHello() {
		return "index";
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, Model model){
		if (validateUser(req.getParameter("email").toString(), req.getParameter("password").toString()))
			return "main";
		else{
			model.addAttribute("invalidUser", "Invalid e-mail or password");
			return "index";
		}
	}
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest req, Model model){
		String errMsg = validateRegistration(req, model);
		if(errMsg.equals("")){
			String text = "Hello " + req.getParameter("firstName").toString() + 
					" !<br>You are successfully registrated at TalentHub";
			System.out.println(req.getParameter("email").toString());
			EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Welcome to TalentHub!", text, req);
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
	public String resetPass(HttpServletRequest req){
		//validate email
			//if valid
			char[] newPass = new char[8];
			Random r = new Random();
			for(int i=0; i < newPass.length; i++){
				newPass[i] = (char)(r.nextInt(26) + 'a');
				if(r.nextBoolean())
					newPass[i] -= 32;
			}
			String pass = new String(newPass);
			System.out.println("New pass " + pass);
			//EmailResponse.getInstance().SendEmail(req.getParameter("email").toString(), "Password reset", "Hello user,<br>Your new password is: " + pass);
			//DBManager call to change pass
			return "resetSuccess";
		//else
			 
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
		if(req.getParameter("email2").toString().equals(""))	
			return "Please confirm your e-mail address";
		if(!req.getParameter("email").toString().equals(req.getParameter("email2").toString()))
			return "E-mail mismatch";
		if(req.getParameter("pass").toString().equals("") || req.getParameter("pass").toString().length() < 8)
			return "Password must be 8 symbols or longer";
		return "";
	}
	private boolean validateUser(String email, String pass) {
		return false;
	}
}
