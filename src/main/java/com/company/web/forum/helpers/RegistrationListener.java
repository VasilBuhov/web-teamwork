package com.company.web.forum.helpers;

import com.company.web.forum.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener
//        implements
//        ApplicationListener<OnRegistrationCompleteEvent>
{

//    @Autowired
//    private IUserService service;
//
//    @Autowired
//    private MessageSource messages;
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Override
//    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
//        this.confirmRegistration(event);
//    }
//
//    private void confirmRegistration(OnRegistrationCompleteEvent event) {
//        User user = event.getUser();
//        String token = UUID.randomUUID().toString();
//        service.createVerificationToken(user, token);
//
//        String recipientAddress = user.getEmail();
//        String subject = "Registration Confirmation";
//        String confirmationUrl
//                = event.getAppUrl() + "/regitrationConfirm?token=" + token;
//        String message = messages.getMessage("message.regSucc", null, event.getLocale());
//
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
//        mailSender.send(email);
//    }
}