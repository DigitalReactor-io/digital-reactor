package io.digitalreactor.core.service;

import io.digitalreactor.model.Site;
import io.digitalreactor.model.TemplateMnemonicEnum;
import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.YandexCounterAccess;
import io.digitalreactor.vendor.yandex.model.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ingvard on 11.09.16.
 */
public class AccountService {
    private SecureRandom random = new SecureRandom();

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    private EmailSenderService emailSenderService;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    public boolean newAccount(String email, String token, Counter counter) {
        String password = generatePassword();
        String hashingPassword = passwordEncoder.encode(password);

        Site site = new Site(counter.getName(), new YandexCounterAccess(token, String.valueOf(counter.getId())));
        Account account = new Account(email, hashingPassword, LocalDate.now(), site);

        accountRepository.save(account);
        emailSenderService.sendNewAccountEmail(email, password);

        return true;
    }

    private String generatePassword() {
        return new BigInteger(43, random).toString(32);
    }
}
