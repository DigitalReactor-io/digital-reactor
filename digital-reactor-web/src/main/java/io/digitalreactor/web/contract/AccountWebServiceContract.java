package io.digitalreactor.web.contract;

import io.digitalreactor.web.dto.EmailCheckUI;

/**
 * Created by ingvard on 10.09.16.
 */
public interface AccountWebServiceContract {
    final String WEB_SERVICE_PATH = "accounts";
    final String EMAIL_CHECK_PATH = "check/email";

    Boolean isEmailFree(EmailCheckUI email);
}
