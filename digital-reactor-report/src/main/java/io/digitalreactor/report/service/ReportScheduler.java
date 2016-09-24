package io.digitalreactor.report.service;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.model.SummaryStatusEnum;
import io.digitalreactor.model.YandexCounterAccess;
import io.digitalreactor.report.builder.VisitsDuringMothReportBuilder;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import io.digitalreactor.vendor.yandex.specification.VisitsRequest;
import io.digitalreactor.web.contract.dto.report.Summary;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingvard on 13.09.16.
 */
public class ReportScheduler {

    private final static Logger logger = LoggerFactory.getLogger(ReportScheduler.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SummaryStatusRepository summaryStatusRepository;
    @Autowired
    private ReportApiService reportApiService;
    @Autowired
    private SummaryRepository summaryRepository;

    @Scheduled(fixedRate = 5000)
    public void executeAvailableTask() {

        List<Object> reportsCollections = new ArrayList<>();

        SummaryStatus summaryStatus = getNewSummaryStatusAndMarkAsLoading();
        if (summaryStatus == null) {
            return;
        }

        logger.info("Got new task for accountId: {} siteId: {}", summaryStatus.getAccountId(), summaryStatus.getSiteId());

        Account account = accountRepository.findByAccountIdAndSiteId(summaryStatus.getAccountId(), summaryStatus.getSiteId());

        //TODO check account == null
        YandexCounterAccess yandexAccess = account.getSites().get(0).getYandexAccess();

        LocalDate lastFullDay = LocalDate.now().minusDays(1);
        logger.info("Got site information for: {} and going to load data", account.getSites().get(0).getName());

        Response response = reportApiService.findAllBy(new VisitsRequest(yandexAccess.getToken(), yandexAccess.getCounterId(), lastFullDay.toString()));
        List<Double> visitMetrika = response.getDatas().get(0).getMetrics().get(0);

        VisitsDuringMothReportBuilder visitsDuringMothReportBuilder = new VisitsDuringMothReportBuilder();

        LocalDate lastMonth = lastFullDay.minusMonths(1);
        LocalDate lastTwoMonth = lastFullDay.minusMonths(2);

        //LocalDate.parse(endIntervalDate).minusMonths(2).withDayOfMonth(1).toString();
        //lastFullDay.get

        //.lengthOfMonth()

        List<Double> current30Days = visitMetrika.subList(visitMetrika.size() - 31, visitMetrika.size() - 1);
        List<Double> monthAgo = visitMetrika.subList(lastTwoMonth.lengthOfMonth(), lastTwoMonth.lengthOfMonth() + lastMonth.lengthOfMonth() - 1);
        List<Double> twoMonthAgo = visitMetrika.subList(0, lastTwoMonth.lengthOfMonth() - 1);

        VisitsDuringMothReportBuilder.VisitsDuringMonthRowData visitsDuringMonthRowData
                = new VisitsDuringMothReportBuilder.VisitsDuringMonthRowData(
                current30Days,
                monthAgo,
                twoMonthAgo,
                lastFullDay
        );

        VisitsDuringMonthReportDto report = visitsDuringMothReportBuilder.build(visitsDuringMonthRowData);

        reportsCollections.add(report);

        summaryRepository.save(new Summary(summaryStatus.getId(), reportsCollections));
        summaryStatus.setStatus(SummaryStatusEnum.DONE);
        summaryStatusRepository.save(summaryStatus);

        logger.info("Task for accountId: {} siteId: {} completed", summaryStatus.getAccountId(), summaryStatus.getSiteId());
    }

    private SummaryStatus getNewSummaryStatusAndMarkAsLoading() {
        SummaryStatus summaryStatus = summaryStatusRepository.findByStatus(SummaryStatusEnum.NEW);
        if (summaryStatus == null) {
            return null;
        }
        summaryStatus.setDate(LocalDate.now());
        summaryStatus.setStatus(SummaryStatusEnum.LOADING);
        summaryStatusRepository.save(summaryStatus);

        return summaryStatus;
    }


}
