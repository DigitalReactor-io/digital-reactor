package io.digitalreactor.web.contract;

import io.digitalreactor.web.contract.dto.CounterUI;
import io.digitalreactor.web.contract.dto.NewAccountUI;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ingvard on 08.09.16.
 */
public interface RegistrationControllerContract {
    final String CONTROLLER_PATH = "registration";
    final String ACTIVATE_CODE_PATH = "activate";
    final String NEW_ACCOUNT_PATH = "account";
    final String COUNTERS_PATH = "counters/{sessionId}";

    final String REDIRECT_PATH = "redirect:/registration.html#sites/session/%s";

    ModelAndView activateRegistrationSession(Long code);

    List<CounterUI> getCountersBySessionId(String sessionId);

    Boolean createNewAccount(NewAccountUI newAccountUI);
}
