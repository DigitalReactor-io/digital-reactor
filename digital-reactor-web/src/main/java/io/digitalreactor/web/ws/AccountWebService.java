package io.digitalreactor.web.ws;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.web.contract.AccountWebServiceContract;
import io.digitalreactor.web.contract.dto.EmailCheckUI;
import io.digitalreactor.web.contract.dto.SiteUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.digitalreactor.web.contract.AccountWebServiceContract.WEB_SERVICE_PATH;

/**
 * Created by MStepachev on 07.09.2016.
 */
@RestController
@RequestMapping(value = WEB_SERVICE_PATH)
public class AccountWebService implements AccountWebServiceContract {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = EMAIL_CHECK_PATH, method = RequestMethod.POST)
    @ResponseBody
    public Boolean isEmailFree(@RequestBody EmailCheckUI email) {
        return !accountRepository.existsByEmail(email.getEmail());
    }

    @RequestMapping(value = SITES_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public List<SiteUI> getSites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Account account = accountRepository.findByEmail(email);

        return account.getSites().stream().map(site -> new SiteUI(site.getName())).collect(Collectors.toList());
    }
}
