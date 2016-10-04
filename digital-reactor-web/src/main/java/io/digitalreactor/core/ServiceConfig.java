package io.digitalreactor.core;

import io.digitalreactor.core.service.AccountService;
import io.digitalreactor.core.service.EmailSenderService;
import io.digitalreactor.core.service.EmailTemplateService;
import io.digitalreactor.core.service.TemporalTokenStorage;
import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.TemplateMnemonicEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by MStepachev on 09.09.2016.
 */
@Configuration
public class ServiceConfig {

    @Value("${mail.server}")
    private String mailServer;
    @Value("${mail.port}")
    private Integer mailPort;
    @Value("${mail.ssl}")
    private Boolean mailSsl;
    @Value("${mail.username}")
    private String mailUser;
    @Value("${mail.password}")
    private String mailPassword;
    @Value("${mail.address.sender}")
    private String mailAddressSender;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccountService accountService(
            AccountRepository accountRepository,
            SummaryStatusRepository summaryStatusRepository,
            PasswordEncoder passwordEncoder,
            EmailSenderService emailSenderService
    ) {
        return new AccountService(accountRepository, summaryStatusRepository, passwordEncoder, emailSenderService);
    }

    @Bean
    public TemporalTokenStorage temporalTokenStorage() {
        return new TemporalTokenStorage();
    }

    @Bean
    public EmailTemplateService emailTemplateService() {
        Map<TemplateMnemonicEnum, String> templatesMnemonic = new HashMap<>();
        templatesMnemonic.put(TemplateMnemonicEnum.WelcomeNewUser, "welcomeNewUser.html");

        return new EmailTemplateService(templatesMnemonic);
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailServer);
        javaMailSender.setPort(mailPort);
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.ssl.enable", String.valueOf(mailSsl));

        javaMailSender.setJavaMailProperties(javaMailProperties);

        javaMailSender.setUsername(mailUser);
        javaMailSender.setPassword(mailPassword);

        return javaMailSender;
    }

    @Bean
    public EmailSenderService emailSenderService(JavaMailSender mailSender, EmailTemplateService emailTemplateService) {
        return new EmailSenderService(mailSender, emailTemplateService, mailAddressSender);
    }
}
