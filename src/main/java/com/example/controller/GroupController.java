package com.example.controller;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Answer;
import model.Comment;
import model.Group;
import model.Post;
import model.Question;
import model.User;
import model.dao.IAnswerDAO;
import model.dao.ICommentDAO;
import model.dao.IGroupDAO;
import model.dao.IGroupDAO.DataSource;
import model.dao.IPostDAO;
@Controller
public class GroupController {
	private ArrayList<Group> allGrps = new ArrayList();
	
	
	@RequestMapping(value="/viewGroups", method = RequestMethod.GET)
	public String groupsMain(Model model){
		try {
			allGrps = IGroupDAO.getDAO(DataSource.DB).getAllGroups();
			model.addAttribute("groups", allGrps);
		} catch (SQLException e) {
			System.out.println("Problem getting groups");
			e.printStackTrace();
		}
		return "groups";
	}
	@RequestMapping(value="/viewGrp", method = RequestMethod.POST)
	public String viewGrp(HttpServletRequest req, Model mod){
		try {
			Group g = allGrps.get(Integer.parseInt(req.getParameter("grpIndex")));
			if(g.getPosts().isEmpty())
				g.setPosts(IPostDAO.getDAO(model.dao.IPostDAO.DataSource.DB).getAllPosts(g));
			mod.addAttribute("posts", g.getPosts());
			req.getSession().setAttribute("group", g);
		} catch (SQLException e) {
			System.out.println("Error getting group posts");
			e.printStackTrace();
		}
		return "group";
	}
	
	
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public String createGroup(){ 
		return "create_group";
	}
	
	@RequestMapping(value="/createNewGroup", method = RequestMethod.POST)
	public String createNewGroup(HttpServletRequest req){ 
		Group g = new Group(req.getParameter("groupName"));
		IGroupDAO.getDAO(DataSource.DB).addGroup((User) req.getSession().getAttribute("loggedUser"), g);
		return "groups";
	}
	@RequestMapping(value="/createPost", method = RequestMethod.GET)
	public String createPost(){
		return "create_group_post";
	}
	
	@RequestMapping(value="/createNewPost", method = RequestMethod.POST)
	public String createNewPost(HttpServletRequest req){
		User u = (User) req.getSession().getAttribute("loggedUser");
		Post p = new Post(u.getEmail(), req.getParameter("text"));
		Group g = (Group) req.getSession().getAttribute("group");
		g.getPosts().add(p);
		try {
			g.setPosts(IPostDAO.getDAO(model.dao.IPostDAO.DataSource.DB).getAllPosts(g));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting posts");
		}
		IPostDAO.getDAO(model.dao.IPostDAO.DataSource.DB).addPost(u, g, p);
		return "groups";
	}
	
	@RequestMapping(value="/viewPost", method = RequestMethod.GET)
	public String viewPost(HttpServletRequest req, Model mod){
		try {
			Group g = (Group) req.getSession().getAttribute("group");
			Post p = g.getPosts().get(Integer.parseInt(req.getParameter("postIndex")));
			if(p.getComments().isEmpty())
				p.setComments(ICommentDAO.getDAO(model.dao.ICommentDAO.DataSource.DB).getAllComments(p));
			mod.addAttribute("post", p);
			mod.addAttribute("comments", p.getComments());
		} catch (SQLException e) {
			System.out.println("Error getting group posts");
			e.printStackTrace();
		}
		return "post_look";
	}
	
	@RequestMapping(value="/addComment", method = RequestMethod.POST)
	public String addComment(HttpServletRequest req, Model mod){
		if(req.getSession().getAttribute("loggedUser") == null)
			return "index";
		User u = (User) req.getSession().getAttribute("loggedUser");
		Post p = (Post) req.getSession().getAttribute("curPost");
		Comment c = new Comment(p.getPost_id(), u.getEmail(), req.getParameter("text"));
		ICommentDAO.getDAO(model.dao.ICommentDAO.DataSource.DB).addComment(p, c, u);
		try {
			p.setComments(ICommentDAO.getDAO(model.dao.ICommentDAO.DataSource.DB).getAllComments(p));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error getting comments");
		}
		return "redirect:/viewGroups";
	}
	
}
