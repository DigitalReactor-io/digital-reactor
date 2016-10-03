package io.digitalreactor.core.service;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.*;
import io.digitalreactor.vendor.yandex.model.Counter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * Created by ingvard on 11.09.16.
 */
public class AccountService {

    private final SummaryStatusRepository summaryStatusRepository;

    private final SecureRandom random = new SecureRandom();

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    public AccountService(
            AccountRepository accountRepository,
            SummaryStatusRepository summaryStatusRepository,
            PasswordEncoder passwordEncoder,
            EmailSenderService emailSenderService
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.summaryStatusRepository = summaryStatusRepository;
    }

    public boolean newAccount(String email, String token, Counter counter) {
        String password = generatePassword();
        String hashingPassword = passwordEncoder.encode(password);

        Site site = new Site(counter.getName(), new YandexCounterAccess(token, String.valueOf(counter.getId())));
        Account account = new Account(email, hashingPassword, LocalDate.now(), site);

        Account save = accountRepository.save(account);
        summaryStatusRepository.save(createNewTaskForLoadSummary(save.getId(), site.getId()));
        emailSenderService.sendNewAccountEmail(email, password);

        return true;
    }

    private SummaryStatus createNewTaskForLoadSummary(String accountId, String siteId) {
        return new SummaryStatus(
                accountId,
                siteId,
                SummaryStatusEnum.NEW,
                LocalDate.now()
        );
    }

    private String generatePassword() {
        return new BigInteger(43, random).toString(32);
    }
}
