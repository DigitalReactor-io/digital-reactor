package io.digitalreactor.report.service;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.model.SummaryStatusEnum;
import io.digitalreactor.model.YandexCounterAccess;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import io.digitalreactor.vendor.yandex.specification.VisitsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingvard on 13.09.16.
 */
public class ReportScheduler {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SummaryStatusRepository summaryStatusRepository;

    @Autowired
    private ReportApiService reportApiService;

    public void executeAvailableTask() {

        List<Object> reportsCollections = new ArrayList<>();

        SummaryStatus summaryStatus = getNewSummaryStatusAndMarkAsLoading();

        Account account = accountRepository.findByAccountIdAndSiteId(summaryStatus.getAccountId(), summaryStatus.getSiteId());

        //TODO check account == null
        YandexCounterAccess yandexAccess = account.getSites().get(0).getYandexAccess();

        Response response = reportApiService.findAllBy(new VisitsRequest(yandexAccess.getToken(), yandexAccess.getCounterId(), LocalDate.now().toString()));



    }

    private SummaryStatus getNewSummaryStatusAndMarkAsLoading() {
        SummaryStatus summaryStatus = summaryStatusRepository.findByStatus(SummaryStatusEnum.NEW);
        summaryStatus.setDate(LocalDate.now());
        summaryStatus.setStatus(SummaryStatusEnum.LOADING);
        summaryStatusRepository.save(summaryStatus);

        return summaryStatus;
    }


}
