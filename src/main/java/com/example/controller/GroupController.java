package com.example.controller;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Group;
import model.dao.IGroupDAO;
import model.dao.IGroupDAO.DataSource;
@Controller
public class GroupController {
	private ArrayList<Group> allGrps = new ArrayList();
	
	
	@RequestMapping(value="/groups", method = RequestMethod.GET)
	public String groupsMain(Model model){
		try {
			allGrps = IGroupDAO.getDAO(DataSource.DB).getAllGroups();
			model.addAttribute("groups", allGrps);
		} catch (SQLException e) {
			System.out.println("Problem getting groups");
			e.printStackTrace();
		}
		return"";
	}
	
	@RequestMapping(value="/viewGroups", method = RequestMethod.GET)
	public String createGroup(HttpServletRequest req, Model model){ 
		return "groups";
	}
	
	
}
