package io.digitalreactor.core.service;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.*;
import io.digitalreactor.vendor.yandex.model.Counter;
import io.digitalreactor.web.service.SummaryService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * Created by ingvard on 11.09.16.
 */
public class AccountService {

     private final SecureRandom random = new SecureRandom();

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    private final SummaryService summaryService;

    public AccountService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            EmailSenderService emailSenderService,
            SummaryService summaryService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.summaryService = summaryService;
    }

    public boolean newAccount(String email, String token, Counter counter) {
        String password = generatePassword();
        String hashingPassword = passwordEncoder.encode(password);

        Site site = new Site(counter.getName(), new YandexCounterAccess(token, String.valueOf(counter.getId())));
        Account account = new Account(email, hashingPassword, LocalDate.now(), site);

        Account save = accountRepository.save(account);
        summaryService.assignToLoading(save.getId(), site.getId());
        emailSenderService.sendNewAccountEmail(email, password);

        return true;
    }



    private String generatePassword() {
        return new BigInteger(43, random).toString(32);
    }
}
