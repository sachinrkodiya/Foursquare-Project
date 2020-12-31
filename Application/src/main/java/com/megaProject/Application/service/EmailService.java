package com.megaProject.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	
	public void sendEmail(String email,String Subject, String text)throws MailException {

        SimpleMailMessage msg = new SimpleMailMessage();
        
        msg.setTo(email);

        msg.setSubject(Subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }

	
	public void sendFeedback(String email, String text)throws MailException {

        SimpleMailMessage msg = new SimpleMailMessage();
        
        msg.setTo("sachin24kodiya@gmail.com");

        msg.setSubject("you got the feedback from -- "+email);
        msg.setText(text);

        javaMailSender.send(msg);

    }
}
