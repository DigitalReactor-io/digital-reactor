package io.digitalreactor.web.contract;

import io.digitalreactor.web.contract.dto.SummaryStatusUI;
import io.digitalreactor.web.contract.dto.report.Summary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by MStepachev on 12.09.2016.
 */

public interface SummaryWebServiceContract {
    final String WEB_SERVICE_PATH = "summaries";
    final String SITE_STATUS_PATH = "status/{siteId}";
    final String RELOAD_SUMMARY_PATH = "reload/{siteId}";
    final String SUMMARY_PATH = "{summaryId}";
    final String SUMMARY_TASK_PATH = "taskId/{summaryTaskId}";

    SummaryStatusUI getSummaryStatus(String siteId);
    Summary getSummary(String summaryTaskId);
    SummaryStatusUI reloadSummary(String siteId);
}
