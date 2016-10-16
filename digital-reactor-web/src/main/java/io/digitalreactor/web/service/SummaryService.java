package io.digitalreactor.web.service;

import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.model.SummaryStatusEnum;

import java.time.LocalDate;

/**
 * Created by ingvard on 16.10.16.
 */
public class SummaryService {

    private final SummaryStatusRepository summaryStatusRepository;

    public SummaryService(SummaryStatusRepository summaryStatusRepository) {
        this.summaryStatusRepository = summaryStatusRepository;
    }

    public SummaryStatus assignToLoading(String accountId, String siteId) {
        SummaryStatus lastSummaryStatus = summaryStatusRepository.findByAccountIdAndSiteId(accountId, siteId);
        if (lastSummaryStatus != null) {
            summaryStatusRepository.delete(lastSummaryStatus);
        }

        return summaryStatusRepository.save(createNewTaskForLoadSummary(accountId, siteId));
    }

    private SummaryStatus createNewTaskForLoadSummary(String accountId, String siteId) {
        return new SummaryStatus(
                accountId,
                siteId,
                SummaryStatusEnum.NEW,
                LocalDate.now()
        );
    }
}
