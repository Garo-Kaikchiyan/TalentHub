package model;

import java.util.ArrayList;

public class Group {
	private String group_name;
	private String user_created;
	private ArrayList<User> members;
	private String message;
	
	public Group(String group_name) {
		super();
		this.group_name = group_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	
}
