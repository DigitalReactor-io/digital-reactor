package io.digitalreactor.report.service;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.model.SummaryStatusEnum;
import io.digitalreactor.model.YandexCounterAccess;
import io.digitalreactor.report.ReportUtil;
import io.digitalreactor.report.builder.ReferringSourceBuilder;
import io.digitalreactor.report.builder.VisitsDuringMothReportBuilder;
import io.digitalreactor.report.model.TwoMonthInterval;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.GoalApiService;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import io.digitalreactor.vendor.yandex.specification.ReferringSourceWitGoalsRequest;
import io.digitalreactor.vendor.yandex.specification.VisitsRequest;
import io.digitalreactor.web.contract.dto.report.Summary;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSourceReport;
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
    @Autowired
    private GoalApiService goalApiService;

    //TODO[St.maxim] this method just a prototype
    @Scheduled(fixedRate = 10000)
    public void executeAvailableTask() {
        SummaryStatus summaryStatus = getNewSummaryStatusAndMarkAsLoading();

        try {
            List<Object> reportsCollections = new ArrayList<>();

            if (summaryStatus == null) {
                return;
            }

            logger.info("Got new task for accountId: {} siteId: {}", summaryStatus.getAccountId(), summaryStatus.getSiteId());

            Account account = accountRepository.findByAccountIdAndSiteId(summaryStatus.getAccountId(), summaryStatus.getSiteId());

            //TODO check account == null
            YandexCounterAccess yandexAccess = account.getSites().get(0).getYandexAccess();
            logger.info("Got site information for: {} and going to load data", account.getSites().get(0).getName());

            reportsCollections.add(visitsDuringMonthReportDto(yandexAccess.getCounterId(), yandexAccess.getToken()));
            reportsCollections.add(referringSourceReport(yandexAccess.getCounterId(), yandexAccess.getToken()));

            summaryRepository.save(new Summary(summaryStatus.getId(), reportsCollections));
            summaryStatus.setStatus(SummaryStatusEnum.DONE);
            summaryStatusRepository.save(summaryStatus);

            logger.info("Task for accountId: {} siteId: {} completed", summaryStatus.getAccountId(), summaryStatus.getSiteId());
        } catch (Exception e) {
            //TODO[St.maxim] only for test
            summaryStatus.setStatus(SummaryStatusEnum.NEW);
            summaryStatusRepository.save(summaryStatus);
            throw e;
        }
    }

    private VisitsDuringMonthReportDto visitsDuringMonthReportDto(String counterId, String token) {
        VisitsDuringMothReportBuilder visitsDuringMothReportBuilder = new VisitsDuringMothReportBuilder();
        TwoMonthInterval interval = TwoMonthInterval.previous(LocalDate.now());
        Response response = reportApiService.findAllBy(new VisitsRequest(token, counterId, interval.first(), interval.last()));

        List<Double> visitMetrika = response.getDatas().get(0).getMetrics().get(0);

        //TODO[St.maxim] input format for this report changed fix it "getSecondMonthMetrics"
        VisitsDuringMothReportBuilder.VisitsDuringMonthRowData visitsDuringMonthRowData
                = new VisitsDuringMothReportBuilder.VisitsDuringMonthRowData(
                ReportUtil.getSecondMonthMetrics(interval, visitMetrika),
                ReportUtil.getFirstMonthMetrics(interval, visitMetrika),
                ReportUtil.getSecondMonthMetrics(interval, visitMetrika),
                interval.last()
        );

        return visitsDuringMothReportBuilder.build(visitsDuringMonthRowData);
    }

    private ReferringSourceReport referringSourceReport(String counterId, String token) {
        TwoMonthInterval interval = TwoMonthInterval.previous(LocalDate.now());
        GoalResponse goals = goalApiService.getGoals(counterId, token);

        Response rowReferringSource = reportApiService.findAllBy(new ReferringSourceWitGoalsRequest(
                token,
                counterId,
                interval.first(),
                interval.last(),
                goals.getGoalsIds()
        ));

        return new ReferringSourceBuilder().build(interval, goals.getGoals(), rowReferringSource.getDatas());
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
