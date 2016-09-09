package io.digitalreactor.web.controller;

import io.digitalreactor.core.service.TemporalTokenStorage;
import io.digitalreactor.vendor.yandex.model.Counter;
import io.digitalreactor.vendor.yandex.serivce.CounterApiService;
import io.digitalreactor.vendor.yandex.serivce.GrantCodeToTokenResolver;
import io.digitalreactor.web.contract.RegistrationControllerContract;
import io.digitalreactor.web.dto.NewAccountUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ingvard on 08.09.16.
 */
@RestController
public class RegistrationController implements RegistrationControllerContract {

    @Autowired
    private TemporalTokenStorage tokenStorage;

    @Autowired
    private GrantCodeToTokenResolver tokenResolver;

    @Autowired
    private CounterApiService counterApiService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @Override
    public ModelAndView activateRegistrationSession(@RequestParam Long code) {
        String token = tokenResolver.getTokenByGrantCode(code);
        tokenStorage.store(token);

        return new ModelAndView(String.format("redirect:/registration.html#access/%s", token));
    }

    public List<Counter> getCounters(String clientToken) {
        return counterApiService.getCounters(clientToken);
    }

    @Override
    public void createNewAccount(NewAccountUI newAccountUI) {

    }
}
