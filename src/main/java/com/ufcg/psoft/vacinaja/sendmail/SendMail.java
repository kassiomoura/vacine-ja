package com.ufcg.psoft.vacinaja.sendmail;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Authenticator;

public class SendMail {
	private static final String EMAIL = "vacine.jagrupo9@gmail.com";
	private static final String MAIL_SMTP_HOST = "smtp.gmail.com";
	private static final String MAIL_SMTP_PORT = "465";
	private static final String MAIL_SMTP_SSL_ENABLE = "true";
	private static final String MAIL_SMTP_AUTH = "true";
	
	public static final String MSG_HABILITADO_PRIMEIRA_DOSE = "Informamos que você esta habilitado para ser vacinado.";
	public static final String MSG_HABILITADO_SEGUNDA_DOSE = "Informamos que você esta habilitado para receber a segunda dose da vacina.";
	
	public static void sendTo(List<String> emails, String mensagem) {
		
		Properties properties = System.getProperties();

	    properties.put("mail.smtp.host", MAIL_SMTP_HOST);
	    properties.put("mail.smtp.port", MAIL_SMTP_PORT);
	    properties.put("mail.smtp.ssl.enable", MAIL_SMTP_SSL_ENABLE);
	    properties.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		
		Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
            	String password = "@Grup09V4";
            	String from = "vacine.jagrupo9@gmail.com";
                return new PasswordAuthentication(from, password);
            }
        });
		
		session.setDebug(true);
		
		for (String email : emails) {
			
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(EMAIL));

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Aviso de habilitação < NÃO RESPONDA ESTE E-MAIL >");
				message.setText(mensagem);
				
				Transport.send(message);
				
				
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
			
		}
		
		
	}

}
