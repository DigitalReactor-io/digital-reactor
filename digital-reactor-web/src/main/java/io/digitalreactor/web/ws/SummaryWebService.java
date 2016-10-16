package io.digitalreactor.web.ws;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.web.contract.SummaryWebServiceContract;
import io.digitalreactor.web.contract.dto.SummaryStatusUI;
import io.digitalreactor.web.contract.dto.report.Summary;
import io.digitalreactor.web.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by MStepachev on 13.09.2016.
 */
@RestController
@RequestMapping(value = SummaryWebServiceContract.WEB_SERVICE_PATH)
public class SummaryWebService implements SummaryWebServiceContract {

    //TODO move to summaryService
    @Autowired
    private SummaryStatusRepository summaryStatusRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @Autowired
    private SummaryService summaryService;

    @RequestMapping(value = SITE_STATUS_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public SummaryStatusUI getSummaryStatus(@PathVariable String siteId) {

        SummaryStatus summaryStatus = summaryStatusRepository.findByAccountIdAndSiteId(getCurrentAccountId(), siteId);

        return mapToUI(summaryStatus);
    }

    @RequestMapping(value = SUMMARY_TASK_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Summary getSummary(@PathVariable String summaryTaskId) {
        return summaryRepository.findByTaskId(summaryTaskId);
    }

    @RequestMapping(value = RELOAD_SUMMARY_PATH, method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public SummaryStatusUI reloadSummary(@PathVariable String siteId) {
        return mapToUI(summaryService.assignToLoading(getCurrentAccountId(), siteId));
    }

    private SummaryStatusUI mapToUI(SummaryStatus summaryStatus) {
        return new SummaryStatusUI(
                summaryStatus.getStatus().name(),
                Date.from(summaryStatus.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                summaryStatus.getId()
        );
    }

    private String getCurrentAccountId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Account account = accountRepository.findByEmail(email);

        return account.getId();
    }

}
