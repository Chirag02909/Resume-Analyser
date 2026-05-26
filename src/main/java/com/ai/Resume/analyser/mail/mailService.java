//package com.ai.Resume.analyser.mail;
//
//
//import brevo.ApiClient;
//import brevo.ApiException;
//import brevo.Configuration;
//import brevoApi.TransactionalEmailsApi;
//import brevoModel.SendSmtpEmail;
//import brevoModel.SendSmtpEmailSender;
//import brevoModel.SendSmtpEmailTo;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import java.util.Collections;
//
//@Service
//public class mailService {
//
//   @Value("${apiKey}")
//   private String apiKey;
//    @Autowired
//    private TemplateEngine templateEngine;
//
//    public void sentVerifyOtp(String username,String email,String otp) throws MessagingException {
//
//        String toEmail=email.substring(0,1)+"*********"+email.substring(email.indexOf("@"),email.length());
//        Context context = new Context();
//        context.setVariable("username",username);
//        context.setVariable("email",toEmail);
//        context.setVariable("otp",otp);
//
//        String mgs = templateEngine.process("verify-otp",context);
//
//        ApiClient apiClient = Configuration.getDefaultApiClient();
//        apiClient.setApiKey(apiKey);
//
//        TransactionalEmailsApi transactionalEmailsApi = new TransactionalEmailsApi(apiClient);
//
//        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
//        sendSmtpEmail.setSender(new SendSmtpEmailSender().name("Resume Analyser").email("cpatel2909@gmail.com"));
//        sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().name(username).email(email)));
//        sendSmtpEmail.setSubject("Email verification OTP");
//        sendSmtpEmail.setHtmlContent(mgs);
//
//        try{
//            transactionalEmailsApi.sendTransacEmail(sendSmtpEmail);
//        } catch (ApiException e) {
//            System.out.println(e.getResponseBody());
//            throw new RuntimeException(e);
//        }
//
//
//
//    }
//
//    public void sentResetOtp(String username,String email,String otp) throws MessagingException {
//
//        String toEmail=email.substring(0,1)+"*********"+email.substring(email.indexOf("@"),email.length());
//        Context context = new Context();
//        context.setVariable("username",username);
//        context.setVariable("email",toEmail);
//        context.setVariable("otp",otp);
//
//        String mgs = templateEngine.process("reset-otp",context);
//
//        ApiClient apiClient = Configuration.getDefaultApiClient();
//        apiClient.setApiKey(apiKey);
//
//        TransactionalEmailsApi transactionalEmailsApi = new TransactionalEmailsApi(apiClient);
//
//        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
//        sendSmtpEmail.setSender(new SendSmtpEmailSender().name("Resume Analyser").email("cpatel2909@gmail.com"));
//        sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().name(username).email(email)));
//        sendSmtpEmail.setSubject("Reset password OTP");
//        sendSmtpEmail.setHtmlContent(mgs);
//
//        try{
//            transactionalEmailsApi.sendTransacEmail(sendSmtpEmail);
//        } catch (ApiException e) {
//            System.out.println(e.getResponseBody());
//            throw new RuntimeException(e);
//        }
//
//
//
//    }
//
//
//}


package com.ai.Resume.analyser.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class mailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sentVerifyOtp(String username, String email, String otp) throws MessagingException {

        String toEmail = email.substring(0,1) + "********" + email.substring(email.indexOf("@"));

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("email", toEmail);
        context.setVariable("otp", otp);

        String msg = templateEngine.process("verify-otp", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(email);
        helper.setSubject("Email Verification OTP");
        helper.setText(msg, true);
        helper.setFrom("yourgmail@gmail.com");

        mailSender.send(mimeMessage);
    }

    public void sentResetOtp(String username, String email, String otp) throws MessagingException {

        String toEmail = email.substring(0,1) + "********" + email.substring(email.indexOf("@"));

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("email", toEmail);
        context.setVariable("otp", otp);

        String msg = templateEngine.process("reset-otp", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(email);
        helper.setSubject("Reset Password OTP");
        helper.setText(msg, true);
        helper.setFrom("yourgmail@gmail.com");

        mailSender.send(mimeMessage);
    }
}