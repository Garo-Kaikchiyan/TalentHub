package model;

public class Demo {
	public static void main(String[] args) {
		String text = "Hello, Zhivko!" + 
				"<br>You are successfully registrated at TalentHub...not!<br>Just want to showoff my mad e-mailing skills!";
		EmailResponse.getInstance().SendEmail("garo.kaikchiyan@yahoo.com", "Welcome", text);
	}
}
