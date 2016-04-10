package com.example.controller;

import java.security.acl.Group;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.dao.IGroupDAO;
import model.dao.IGroupDAO.DataSource;
@Controller
public class GroupController {
	private ArrayList<Group> allGrps = new ArrayList();
	
	
	@RequestMapping(value="/groups", method = RequestMethod.GET)
	public String groupsMain(){
		//IGroupDAO.getDAO(DataSource.DB).
		return"";
	}
	
	@RequestMapping(value="/viewGroups", method = RequestMethod.GET)
	public String createGroup(HttpServletRequest req, Model model){ 
		return "groups";
	}
	
	
}
