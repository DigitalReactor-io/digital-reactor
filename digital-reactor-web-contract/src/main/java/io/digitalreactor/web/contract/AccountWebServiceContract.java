package io.digitalreactor.web.contract;

import io.digitalreactor.web.contract.dto.EmailCheckUI;
import io.digitalreactor.web.contract.dto.ShortUserInfoUI;
import io.digitalreactor.web.contract.dto.SiteUI;

import java.util.List;

/**
 * Created by ingvard on 10.09.16.
 */
public interface AccountWebServiceContract {
    final String WEB_SERVICE_PATH = "accounts";
    final String EMAIL_CHECK_PATH = "check/email";
    final String SITES_PATH = "sites";
    final String SHORT_INFO = "my/short";

    ShortUserInfoUI getShortInfo();

    Boolean isEmailFree(EmailCheckUI email);

    List<SiteUI> getSites();
}
