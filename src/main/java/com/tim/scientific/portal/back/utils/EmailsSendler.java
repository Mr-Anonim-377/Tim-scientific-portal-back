package com.tim.scientific.portal.back.utils;

import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class EmailsSendler {

    public void sendEmail(String subject, String html, String emailTo) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Создаем соединение для отправки почтового сообщения
        Session session = getSession(properties);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("AgroShop-365@yandex.ru"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        message.setSubject(subject);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart part = new MimeBodyPart();
        part.addHeader("Content-Type", "text/html; charset=UTF-8");
        part.setDataHandler(
                new DataHandler(html, "text/html; charset=\"utf-8\""
                )
        );
        multipart.addBodyPart(part);
        message.setContent(multipart);

        Transport.send(message);
    }

    private Session getSession(Properties properties) {
        return Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("AgroShop-365@yandex.ru",
                                "CSkl19nigypqjexm");
                    }
                });
    }
}

