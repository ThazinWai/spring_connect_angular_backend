package com.techie.springconnect.service;


import com.techie.springconnect.exceptions.SpringConnectException;
import com.techie.springconnect.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springConnect@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getReciepient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(notificationEmail.getBody());
        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email send!");
        }catch (MailException e){
            log.error("Exception error occur when sending mail", e);
            throw new SpringConnectException("Exception occur when sending mail" + notificationEmail.getReciepient(), e);
        }
    }
}
