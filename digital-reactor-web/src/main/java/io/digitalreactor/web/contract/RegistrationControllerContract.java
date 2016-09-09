package io.digitalreactor.web.contract;

import io.digitalreactor.web.dto.NewAccountUI;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ingvard on 08.09.16.
 */
public interface RegistrationControllerContract {
    ModelAndView activateRegistrationSession(Long code);

    void createNewAccount(NewAccountUI newAccountUI);
}
