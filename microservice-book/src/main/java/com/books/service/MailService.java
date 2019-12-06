package com.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

	@Autowired
    public JavaMailSender emailSender;
	
	/**
     * Function parameter email
     * 
     * @param from
     *            the person who send the email
     * @param to
     *            the person who receive the email
     * @param subject
     *            the object of the email
     * @param text
     *            the content of the email
     */
    public void sendMessage( String to, String subject, String text ) {

        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom( from );
        message.setTo( to );
        message.setSubject( subject );
        message.setText( text );
        emailSender.send( message );
    }
}
