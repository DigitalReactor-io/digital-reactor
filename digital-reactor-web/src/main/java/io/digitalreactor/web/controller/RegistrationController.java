package io.digitalreactor.web.controller;

import io.digitalreactor.core.service.AccountService;
import io.digitalreactor.core.service.TemporalTokenStorage;
import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.Site;
import io.digitalreactor.model.YandexCounterAccess;
import io.digitalreactor.vendor.yandex.model.Counter;
import io.digitalreactor.vendor.yandex.serivce.CounterApiService;
import io.digitalreactor.vendor.yandex.serivce.GrantCodeToTokenResolver;
import io.digitalreactor.web.contract.RegistrationControllerContract;
import io.digitalreactor.web.dto.CounterUI;
import io.digitalreactor.web.dto.NewAccountUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.digitalreactor.web.contract.RegistrationControllerContract.CONTROLLER_PATH;

/**
 * Created by ingvard on 08.09.16.
 */
@RestController
@RequestMapping(value = CONTROLLER_PATH)
public class RegistrationController implements RegistrationControllerContract {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private TemporalTokenStorage tokenStorage;

    @Autowired
    private GrantCodeToTokenResolver tokenResolver;

    @Autowired
    private CounterApiService counterApiService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = ACTIVATE_CODE_PATH, method = RequestMethod.GET)
    @Override
    public ModelAndView activateRegistrationSession(@RequestParam Long code) {
        String token = tokenResolver.getTokenByGrantCode(code);
        String sessionId = tokenStorage.store(token);

        return new ModelAndView(String.format(REDIRECT_PATH, sessionId));
    }

    @RequestMapping(value = COUNTERS_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public List<CounterUI> getCountersBySessionId(@PathVariable String sessionId) {
        String token = tokenStorage.get(sessionId);
        List<Counter> counters = counterApiService.getCounters(token);

        //TODO[St.maxim] use mapper
        return counters.stream().map(CounterUI::new).collect(Collectors.toList());
    }

    @RequestMapping(value = NEW_ACCOUNT_PATH, method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Boolean createNewAccount(@RequestBody NewAccountUI newAccountUI) {
        String token = tokenStorage.poll(newAccountUI.getSessionId());

        return accountService.newAccount(
                newAccountUI.getEmail(),
                token,
                new Counter(newAccountUI.getCounterId(), newAccountUI.getName())
        );
    }
}
