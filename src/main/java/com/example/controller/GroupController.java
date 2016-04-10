package com.example.controller;

import java.security.acl.Group;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
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
}
