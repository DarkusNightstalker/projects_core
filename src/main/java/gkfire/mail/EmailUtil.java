/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 *
 * @author Max
 */
public class EmailUtil {
    private String user = "danny.lopez@unas.edu.pe";
    private String password = "Bker1989";

    private String title;
    private String content;

    public EmailUtil() {
        this.user = "danny.lopez@unas.edu.pe";
        this.password = "Bker1989";
    }

    public EmailUtil(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitleMessage(String title) {
        this.title = title;
    }

    public void sendEmailMultiple(String[] emails) {
        ////////////////////////////////////
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.outlook.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = user;
                String pass = password;
                return new PasswordAuthentication(username, pass);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(user));

            InternetAddress[] addressTo = new InternetAddress[emails.length];
            for (int i = 0; i < emails.length; i++) {
                addressTo[i] = new InternetAddress(emails[i]);
            }
            message.setRecipients(RecipientType.TO, addressTo);

            // Set Subject: header field
            //"DESIGNACIÓN DE JURADOS DE PRÁCTICAS PRE PROFESIONALES"
            message.setSubject(title);

            // Send the actual HTML message, as big as you like
            message.setContent("<html><body>ESTUPIDO</body></html>",
                    "text/html");
//            message.setContent(props, title);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

//    public static void main(String args[]) {
//        String[] to = {"max.dicson.cf@gmail.com", "dcondor.cjavaperu@gmail.com"};
//        EmailUtil m = new EmailUtil();
//        m.sendEmailMultiple(to);
//    }
}
