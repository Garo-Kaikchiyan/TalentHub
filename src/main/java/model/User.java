package model;

import java.sql.Date;

public class User {
	private String firstName, lastName, email, password, gender, photo;
	private Date birth;
	
	public User(String firstName, String lastName, String email, String password, String gender, Date birth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		if(gender.equals("male"))
			photo = "https://cdn1.iconfinder.com/data/icons/user-pictures/100/male3-512.png";
		else
			photo = "http://www.iconsfind.com/wp-content/uploads/2015/08/20150831_55e46b1c0c44b.png";
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	

}
