package ua.khnu.shtefanyankovska.util.mail;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Message {

    private static final Logger LOG = Logger.getLogger(Message.class.getName());
    private static Properties props;

    private Message() {
        // nothing to do
    }

    /**
     * Sending message to the current user with congratulations about registration
     * in the system
     *
     * @param address - send to
     * @param content - content of the mail
     * @throws NamingException
     */

    public static void sendTextMessage(String address, String content) throws NamingException {

      props = new Properties();
      props.put("name", "mail/courseWork");
      props.put("auth", "Container");
      props.put("type", "javax.mail.Session");
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.from", "testing.sendler@gmail.com");
      props.put("mail.smtp.password", "testingsendler12");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.debug", "true");
      props.put("mail.mime.charset", "UTF-8");
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactor");

      Session session = Session.getInstance(props);

      LOG.info("Starting sending message");

      MimeMessage message = new MimeMessage(session);

      try {
        message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(address));
        message.setSubject(MimeUtility.encodeText("Поздравление", "UTF-8", "B"));
        message.setText(content, "cp1251", "html");

        Transport transport = session.getTransport("smtp");
        transport.connect(session.getProperty("mail.smtp.from"), session.getProperty("mail.smtp.password"));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
      } catch (MessagingException e) {
        LOG.error("Exception occurred while sending message " + e.getMessage());
        LOG.info("Message sending finished unsuccessfully");
        return;
      } catch (UnsupportedEncodingException e) {
        LOG.error("Exception occurred while sending message");
        LOG.info("Message sending finished unsuccessfully");
        return;
      }

      LOG.info("Message sending finished successfully");
    }

    /**
     * Sending mail to the user with statistics of his/her passing tests
     *
     * @param address - send to
     * @param content - content of the mail
     * @param file    - file with statistics
     * @throws NamingException
     */
    public static void sendFileMessage(String address, String content, File file) {
      Thread t = new Thread(() -> {
        try {
          Context envCtx = new InitialContext(props);
          Session session = (Session) envCtx.lookup("mail/courseWork");

          LOG.info("Starting sending message with statistics");

          MimeMessage message = new MimeMessage(session);

          message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(address));
          message.setSubject(MimeUtility.encodeText("Статистика", "UTF-8", "B"));

          Multipart multipart = new MimeMultipart();

          MimeBodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setText(content, "cp1251", "html");

          multipart.addBodyPart(messageBodyPart);

          messageBodyPart = new MimeBodyPart();
          String filename = file.getName();
          DataSource source = new FileDataSource(file);
          messageBodyPart.setDataHandler(new DataHandler(source));
          messageBodyPart.setFileName(filename);

          multipart.addBodyPart(messageBodyPart);

          message.setContent(multipart);
          Transport transport = session.getTransport("smtp");
          transport.connect(session.getProperty("mail.smtp.from"), session.getProperty("mail.smtp.password"));
          transport.sendMessage(message, message.getAllRecipients());
          transport.close();
          LOG.info("Message with statistics sending finished successfully");
          Thread.sleep(1500);
          file.delete();
          return;
        } catch (Exception e) {
          LOG.error("Error in message thread");
          LOG.info("Message with statistics sending finished unsuccessfully");
        }
      });

      t.start();

    }

}