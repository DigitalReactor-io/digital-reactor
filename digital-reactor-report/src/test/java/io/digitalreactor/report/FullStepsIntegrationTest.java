package io.digitalreactor.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.*;
import io.digitalreactor.report.service.ReportScheduler;
import io.digitalreactor.vendor.yandex.domain.Request;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.GoalApiService;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import io.digitalreactor.vendor.yandex.specification.ReferringSourceWitGoalsRequest;
import io.digitalreactor.vendor.yandex.specification.VisitsRequest;
import io.digitalreactor.web.contract.dto.report.Summary;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ingvard on 09.10.16.
 */
public class FullStepsIntegrationTest {

    private final String ACCOUNT_ID = "1";
    private final String SITE_ID = "2";
    private final String COUNTER_TOKEN = "counterToken";
    private final String COUNTER_ID = "counterId";


    private Summary preparedSummary;

    public FullStepsIntegrationTest() {
        SummaryRepository summaryRepository = mock(SummaryRepository.class);

        SummaryStatusRepository summaryStatusRepository = mock(SummaryStatusRepository.class);
        when(summaryStatusRepository.findByStatus(SummaryStatusEnum.NEW)).thenReturn(new SummaryStatus(ACCOUNT_ID, SITE_ID, SummaryStatusEnum.NEW, LocalDate.now()));

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByAccountIdAndSiteId(ACCOUNT_ID, SITE_ID)).thenReturn(new Account(null, null, null, new Site("test", new YandexCounterAccess(COUNTER_TOKEN, COUNTER_ID))));

        GoalApiService goalApiService = mock(GoalApiService.class);
        when(goalApiService.getGoals(COUNTER_ID, COUNTER_TOKEN)).thenReturn(new GoalResponse());


        ReportScheduler reportScheduler = new ReportScheduler();
        reportScheduler.setSummaryRepository(summaryRepository);
        reportScheduler.setSummaryStatusRepository(summaryStatusRepository);
        reportScheduler.setAccountRepository(accountRepository);
        reportScheduler.setGoalApiService(goalApiService);
        reportScheduler.setReportApiService(new ReportApiServiceTestHelper(null));

        reportScheduler.executeAvailableTask();


        //Catch result
        final ArgumentCaptor<Summary> captor = ArgumentCaptor.forClass(Summary.class);
        verify(summaryRepository).save(captor.capture());
        this.preparedSummary = captor.getValue();
    }

    @Test
    public void makeReferringSourceReportByRegressionData() {
        VisitsDuringMonthReportDto visitsDuringMonthReport = extractReportWithType(VisitsDuringMonthReportDto.class);

        assertThat(visitsDuringMonthReport.getVisit(), is(equalTo(232)));
        assertThat(visitsDuringMonthReport.getAction(), is(equalTo(232)));
        assertThat(visitsDuringMonthReport.getPercent(), is(equalTo(232)));
    }

    private <T> T extractReportWithType(Class<T> reportType) {

        Optional<T> first = (Optional<T>) preparedSummary.getReports().stream()
                .filter(reportType::isInstance)
                .findFirst();

        if (first.isPresent()) {
            return first.get();
        }

        throw new IllegalArgumentException(String.format("Report with type %s not found", reportType.getName()));
    }

    private static class ReportApiServiceTestHelper extends ReportApiService {

        private final ObjectMapper mapper = new ObjectMapper();

        public ReportApiServiceTestHelper(CloseableHttpClient httpClient) {
            super(httpClient);
        }

        @Override
        public Response findAllBy(Request request) {
            try {
                String row = null;
                if (request instanceof VisitsRequest) {
                    row = FileUtils.readFileToString(new File("src/test/resources/dataset/visits.json"), "UTF-8");
                } else if (request instanceof ReferringSourceWitGoalsRequest) {
                    row = FileUtils.readFileToString(new File("src/test/resources/dataset/refereceSources.json"), "UTF-8");
                }

                return mapper.readValue(row, Response.class);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}