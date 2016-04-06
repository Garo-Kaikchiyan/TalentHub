package model;
import java.sql.*;
import java.time.LocalDate;

public class Test {


	public static final String DB_NAME = "talenthub";
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	private static final String DB_USER = "root";
	private static final String DB_PASS = "";
	private Connection con;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try(Connection con=DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);){
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connection established");
			java.sql.Statement stm= con.createStatement();
			ResultSet rs=stm.executeQuery("SELECT birth_date FROM TalentHub.Users;");
			
			System.out.println("Query made");
			rs.next();
			System.out.println("RS moved one field");
			//Date date=rs.getDate(1);
			LocalDate date=rs.getDate(1).toLocalDate();
			System.out.println("date grabbed from rs");
			System.out.println(date.getDayOfMonth()+" "+date.getMonthValue()+" "+date.getYear());
			System.out.println(date.toString());
		} catch (SQLException | ClassNotFoundException e){
			System.out.println(e.toString());
		} finally {
			System.out.println("I made my point over here");
		}
	}

}
