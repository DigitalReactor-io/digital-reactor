package io.digitalreactor.core.service;

import io.digitalreactor.model.TemplateMnemonicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingvard on 11.09.16.
 */
public class EmailSenderService {
    private final static Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    private EmailTemplateService emailTemplateService;

    private JavaMailSender mailSender;

    private final String senderEmail;

    private final String NEW_ACCOUNT_TITLE = "Регистрация нового пользователя";

    public EmailSenderService(JavaMailSender mailSender, EmailTemplateService emailTemplateService, String senderEmail) {
        this.emailTemplateService = emailTemplateService;
        this.mailSender = mailSender;
        this.senderEmail = senderEmail;
    }


    public void sendNewAccountEmail(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("email", email);

        String template = emailTemplateService.prepare(TemplateMnemonicEnum.WelcomeNewUser, params);

        try {
            sendEmail(email, senderEmail, NEW_ACCOUNT_TITLE, template);
        } catch (MessagingException e) {
            logger.error("New account message did't send for {} because: ", email, e);
        }
    }

    public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
        mimeMessage.setContent(msgBody, "text/html; charset=UTF-8");
        helper.setTo(toAddress);
        mimeMessage.setFrom(fromAddress);
        mimeMessage.setSubject(subject);

        mailSender.send(mimeMessage);
    }
}
