package io.digitalreactor.web.contract;

import io.digitalreactor.web.contract.dto.SummaryStatusUI;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by MStepachev on 12.09.2016.
 */

public interface SummaryWebServiceContract {
    final String WEB_SERVICE_PATH = "summaries";
    final String SITE_STATUS_PATH = "status/{siteName}";

    SummaryStatusUI getSummaryStatus(String siteName);
}
