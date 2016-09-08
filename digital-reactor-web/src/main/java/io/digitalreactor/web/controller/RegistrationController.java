package io.digitalreactor.web.controller;

import io.digitalreactor.core.service.AccessTokenResolver;
import io.digitalreactor.core.service.TemporalTokenStorage;
import io.digitalreactor.web.contract.RegistrationControllerContract;
import io.digitalreactor.web.dto.NewAccountUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ingvard on 08.09.16.
 */
@RestController
public class RegistrationController implements RegistrationControllerContract {

    //@Autowired
    private TemporalTokenStorage tokenStorage;

   // @Autowired
    private AccessTokenResolver tokenResolver;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @Override
    public ModelAndView activateRegistrationSession(@RequestParam String code) {
      /*  String token = tokenResolver.solve(code);
        tokenStorage.store(token);*/

        return new ModelAndView("redirect:" + "/registration.html#access/1234");
    }

    @Override
    public void createNewAccount(NewAccountUI newAccountUI) {

    }
}
