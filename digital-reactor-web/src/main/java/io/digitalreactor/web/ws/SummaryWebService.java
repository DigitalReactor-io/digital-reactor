package io.digitalreactor.web.ws;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.web.contract.SummaryWebServiceContract;
import io.digitalreactor.web.contract.dto.SummaryStatusUI;
import io.digitalreactor.web.contract.dto.report.Summary;
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

    @Autowired
    private SummaryStatusRepository summaryStatusRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @RequestMapping(value = SITE_STATUS_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public SummaryStatusUI getSummaryStatus(@PathVariable String siteId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Account account = accountRepository.findByEmail(email);
        SummaryStatus summaryStatus = summaryStatusRepository.findByAccountIdAndSiteId(account.getId(), siteId);

        return new SummaryStatusUI(
                summaryStatus.getStatus().name(),
                Date.from(summaryStatus.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                summaryStatus.getId()
        );
    }

    @RequestMapping(value = SUMMARY_TASK_PATH, method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Summary getSummary(@PathVariable String summaryTaskId) {
        return summaryRepository.findByTaskId(summaryTaskId);
    }
}
