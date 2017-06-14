package gkfire.mail;

import gkfire.util.AES;
import java.util.Properties;
import javax.faces.context.FacesContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 * itcuties mail example
 *
 * @author itcuties
 *
 */
public class JavaMailSender {

    public static final String TEMPLATE = "<div align=\"center\">\n"
            + "<img src=\"http://fotos.subefotos.com/194e03cb24755577b8ad29cbcaa7a487o.png\">\n"            
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"550\" class=\"x_responsive\" style=\"font-family:Helvetica,Arial,sans-serif; min-width:290px\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td style=\"font-family:Helvetica,Arial,sans-serif\">\n"            
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#333333\" style=\"font-family:Helvetica,Arial,sans-serif\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td width=\"20\" class=\"x_responsive-spacer\">\n"
            + "<table width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "<td width=\"100%\">\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:14px; font-size:14px; line-height:14px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"font-family:Helvetica,Arial,sans-serif\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td width=\"100%\" valign=\"middle\" align=\"left\" style=\"color:#FFFFFF; font-size:20pt; font-family:Helvetica,Arial,sans-serif\">\n"
            + "<span style=\"color:#FFFFFF; font-size:15pt; font-family:Helvetica,Arial,sans-serif\">@:@titulo</span></td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:14px; font-size:14px; line-height:14px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "<td width=\"20\" class=\"x_responsive-spacer\">\n"
            + "<table width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#FFFFFF\" style=\"font-family:Helvetica,Arial,sans-serif\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td width=\"20\" class=\"x_res-width10\">\n"
            + "<table width=\"20px\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer x_res-width10\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "<td align=\"left\" style=\"color:#333333; font-family:Helvetica,Arial,sans-serif; font-size:15px; line-height:18px\">\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer x_res-height10\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:20px; font-size:20px; line-height:20px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"font-family:Helvetica,Arial,sans-serif; font-size:16px; font-family:Helvetica,Arial,sans-serif; color:#333333\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>@:@contenido</td>\n"
            + "</tr>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:15px; font-size:15px; line-height:15px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td align=\"center\" height=\"30\" valign=\"middle\" bgcolor=\"#ffe86c\" background=\"http://s.c.lnkd.licdn.com/scds/common/u/img/email/bg_btn_katy_yellow_medium.png\" style=\"background-color:#ffe86c; border:1px solid #e8b463; -moz-border-radius:3px; -webkit-border-radius:3px; border-radius:3px\">\n"
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"transparent\" style=\"font-family:Helvetica,Arial,sans-serif\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td width=\"13\">\n"
            + "<table width=\"13px\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "<td><a href=\"@:@link\" target=\"_blank\" style=\"text-decoration:none; font-size:13px; font-family:Helvetica,Arial,sans-serif; font-weight:bold; color:#000000; white-space:nowrap; display:block\">Ir a la pagina principal</a></td>\n"
            + "<td width=\"13\">\n"
            + "<table width=\"13px\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "<td width=\"20\" class=\"x_res-width10\">\n"
            + "<table width=\"20px\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer x_res-width10\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:0px; font-size:0px; line-height:0px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"550\" bgcolor=\"#FFFFFF\" style=\"font-family:Helvetica,Arial,sans-serif; font-size:16px; font-family:Helvetica,Arial,sans-serif; color:#333333; background-color:#FFFFFF\">\n"
            + "<tbody>\n"
            + "<tr style=\"background-color:#FFFFFF\">\n"
            + "<td>\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:20px; font-size:20px; line-height:20px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr>\n"
            + "<td>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr style=\"background-color:#FFFFFF\">\n"
            + "<td>\n"
            + "<table width=\"1\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"height:20px; font-size:20px; line-height:20px\">&nbsp; </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr style=\"background-color:#333333\">\n"
            + "<td>\n"
            + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\"  class=\"x_email-spacer\">\n"
            + "<tbody>\n"
            + "<tr>\n"
            + "<td>\n"
            + "<div style=\"color:#FFFFFF;  font-family:Helvetica,Arial,sans-serif;padding:12px;text-align:right\"><small>SPPP - Sistema de Prácticas Pre-Profesionales. Copyright© 2015 </small> </div>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>\n"
            + "</div>";

    public static void send(Integer id,String tipo,String email,String password,String contenido,String titulo,String linkContent,String subject,String[] destinos) throws Exception {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }
        String completeURL = requestURL.toString().replace("index.xhtml", "service/email.xhtml?q=");
        String q = tipo+"," + id + ","+linkContent;
        //completeURL += AES.encryptQueryLinkEmail(q);
        String content = JavaMailSender.TEMPLATE
                .replace("@:@titulo", titulo)
                .replace("@:@contenido", contenido)
                .replace("@:@link", completeURL);
        JavaMailSender.threadEmail(RequestContext.getCurrentInstance(), email, password,content,subject,destinos);

    }

    public static void threadEmail(
            final RequestContext c,
            final String e,
            final String p,
            final String m,
            final String s,
            final String[] st) {

        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JavaMailSender.sendEmail(e, p, m,s, st);
                    c.execute("AppMessages.runMessage('Mensaje enviado','Correo enviando','success','fa fa-envelope')");
                } catch (Exception e) {
                    c.execute("AppMessages.runMessage('Mensaje no enviado al correo','Por favor verifique su conexión a internet','danger','fa fa-envelope')");
                }
            }
        });
        h.start();
    }

    /**
     * Send the email via SMTP using TLS and SSL
     */
    public static void sendEmail(final String email, final String password, final String content,final String subject,String[] sendTo) throws MessagingException {

        Properties connectionProperties = new Properties();
        connectionProperties.put("mail.smtp.host", "smtp.outlook.com");
        connectionProperties.put("mail.smtp.auth", "true");
        connectionProperties.put("mail.smtp.starttls.enable", "true");
        connectionProperties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(connectionProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });

        Message message = new MimeMessage(session);
        InternetAddress[] addressTo = new InternetAddress[sendTo.length];
        for (int i = 0; i < sendTo.length; i++) {
            addressTo[i] = new InternetAddress(sendTo[i]);
        }
        message.setFrom(new InternetAddress(email));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("johan.zevallos@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, addressTo);
        message.setContent(content, "text/html");
        message.setSubject(subject);

        Transport.send(message);
        System.out.println("OK");
    }

}
