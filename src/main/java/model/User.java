package model;

import java.sql.Date;

public class User {
	private String firstName, lastName, email, password, gender, photo;
	private Date birth;
	private int phpAnswers, jsAnswers, androidAnswers, eeAnswers;
	private int allForumEntrys;
	private int phpLikes;
	private int jsLikes;
	private int javaLikes;
	private int androidLikes;

	public User(String firstName, String lastName, String email, String password, String gender, Date birth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		if (gender.equals("male"))
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
		 if(this.photo == null){
		 if(gender.equals("male"))
		 this.photo =
		 "https://cdn1.iconfinder.com/data/icons/user-pictures/100/male3-512.png";
		 else
		 this.photo =
		 "http://www.iconsfind.com/wp-content/uploads/2015/08/20150831_55e46b1c0c44b.png";
		 }
	}

	public int getPhpAnswers() {
		return phpAnswers;
	}

	public void setPhpAnswers(int phpAnswers) {
		this.phpAnswers = phpAnswers;
	}

	public int getJsAnswers() {
		return jsAnswers;
	}

	public void setJsAnswers(int jsAnswers) {
		this.jsAnswers = jsAnswers;
	}

	public int getAndroidAnswers() {
		return androidAnswers;
	}

	public void setAndroidAnswers(int androidAnswers) {
		this.androidAnswers = androidAnswers;
	}

	public int getEeAnswers() {
		return eeAnswers;
	}

	public void setEeAnswers(int eeAnswers) {
		this.eeAnswers = eeAnswers;
	}

	public int getAllForumEntrys() {
		return allForumEntrys;
	}

	public void setAllForumEntrys(int allForumEntrys) {
		this.allForumEntrys = allForumEntrys;
	}

	public int getPhpLikes() {
		return phpLikes;
	}

	public void setPhpLikes(int phpLikes) {
		this.phpLikes = phpLikes;
	}

	public int getJsLikes() {
		return jsLikes;
	}

	public void setJsLikes(int jsLikes) {
		this.jsLikes = jsLikes;
	}

	public int getJavaLikes() {
		return javaLikes;
	}

	public void setJavaLikes(int javaLikes) {
		this.javaLikes = javaLikes;
	}

	public int getAndroidLikes() {
		return androidLikes;
	}

	public void setAndroidLikes(int androidLikes) {
		this.androidLikes = androidLikes;
	}

	
}
