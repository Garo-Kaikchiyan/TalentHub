package model;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
public class EmailResponse {
	
	private static final String from = "garomaildemo@gmail.com";
	private static final String pass = "ittalent";
	private static final String host = "smtp.gmail.com";
	public static EmailResponse instance;
	
	private EmailResponse(){}
	
	public static EmailResponse getInstance(){
		if(instance == null)
			instance = new EmailResponse();
		return instance;
	}
	
	public boolean SendEmail(String toAdress, String subject, String text, HttpServletRequest req){
		

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(from, pass);
	            }
	         });

	      try {

	         Message message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from, "Team TalentHub"));
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(toAdress));
	         message.setSubject(subject);
	         MimeMultipart multipart = new MimeMultipart("related");
	         BodyPart messageBodyPart = new MimeBodyPart();
	         String htmlText = "<H4>"+text+"</H4><br><br>------------------------<br>The Talent Hub team<br><img src=\"cid:image\">";
	         messageBodyPart.setContent(htmlText, "text/html");
	         multipart.addBodyPart(messageBodyPart);
	         messageBodyPart = new MimeBodyPart();

	         
	         DataSource fds = new FileDataSource(
	        		 req.getServletContext().getRealPath("") + "/static/images/logo-black.png");

	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");

	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart);
	         Transport.send(message);
	         System.out.println("Mail send to " + toAdress);
	         return true;

	      } catch (MessagingException e) {
	    	  System.out.println("Error sending mail ");
	    	  e.printStackTrace();
	    	  return false;
	      } catch (UnsupportedEncodingException e) {
			System.out.println("Custom e-mail name error");
			e.printStackTrace();
			return false;
		}
	}
}
