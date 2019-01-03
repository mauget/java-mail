package com.rogersalumni;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendGmx {

    private static String MSG_SUBJECT = "Test of javax.mail";
    private static String MSG_TEXT = "<h1>Lorem Bacon</h1><p>Filet mignon landjaeger cupim, porchetta tail " +
            "pancetta ball tip ribeye meatball turducken meatloaf tenderloin prosciutto swine. Pastrami " +
            "tri-tip pig buffalo. Fatback alcatra ham hock landjaeger, pork belly kevin bresaola pork chop " +
            "venison hamburger shankle meatloaf. Swine sirloin drumstick boudin. Fatback porchetta shank " +
            "pork chop chuck tongue, pastrami flank andouille buffalo sausage shankle cupim kielbasa.</p>";

    public static void main(String[] args) {

        logTheArgs(args);

        if (args.length == 5) {
            try {
                send(args);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Need command-line params: <to> <from> <host> <user> <password>");
        }
    }

    public static void send(String[] args) throws MessagingException {
        String to = args[0];
        String from = args[1];
        String host = args[2];
        String user = args[3];
        String password = args[4];

        Properties properties = new Properties();

        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user", user);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        properties.getProperty("mail.smtp.user"),
                        properties.getProperty("mail.smtp.password"));
            }
        });

        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setFrom(new InternetAddress(from));
        message.setSubject(MSG_SUBJECT);
        message.setContent(MSG_TEXT, "text/html");

        Transport.send(message);
    }

    static private void logTheArgs(String[] args){
        int seq = 0;
        final String[] labels = new String[]{"to", "from", "host", "user", "password"};
        System.out.println("\nargs:");
        for(String item : args){
            System.out.println(String.format("\t%d) %s: \"%s\" ", seq+1, labels[seq], item));
            seq++;
        }
        System.out.println("\n");
    }

}
