package io.digitalreactor.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.dao.SummaryRepository;
import io.digitalreactor.dao.SummaryStatusRepository;
import io.digitalreactor.model.*;
import io.digitalreactor.report.service.ReportScheduler;
import io.digitalreactor.vendor.yandex.domain.CustomRequest;
import io.digitalreactor.vendor.yandex.domain.Request;
import io.digitalreactor.vendor.yandex.model.Goal;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import io.digitalreactor.vendor.yandex.model.PhraseRow;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.GoalApiService;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import io.digitalreactor.vendor.yandex.specification.DirectSearchPhrasesWithGoalsRequest;
import io.digitalreactor.vendor.yandex.specification.ReferringSourceWitGoalsRequest;
import io.digitalreactor.vendor.yandex.specification.VisitsRequest;
import io.digitalreactor.web.contract.dto.report.ActionEnum;
import io.digitalreactor.web.contract.dto.report.Summary;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;
import io.digitalreactor.web.contract.dto.report.direct.DirectSearchPhraseReportDto;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSource;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSourceReport;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
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
    private final Integer GOAL_ID = 18970640;
    private final String GOAL_NAME = "Оформленная заявка";


    private Summary preparedSummary;

    //TODO[St.maxim] move to abstract Test
    public FullStepsIntegrationTest() {
        SummaryRepository summaryRepository = mock(SummaryRepository.class);

        SummaryStatusRepository summaryStatusRepository = mock(SummaryStatusRepository.class);
        when(summaryStatusRepository.findByStatus(SummaryStatusEnum.NEW)).thenReturn(new SummaryStatus(ACCOUNT_ID, SITE_ID, SummaryStatusEnum.NEW, LocalDate.now()));

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByAccountIdAndSiteId(ACCOUNT_ID, SITE_ID)).thenReturn(new Account(null, null, null, new Site("test", new YandexCounterAccess(COUNTER_TOKEN, COUNTER_ID))));

        GoalApiService goalApiService = mock(GoalApiService.class);
        when(goalApiService.getGoals(COUNTER_ID, COUNTER_TOKEN)).thenReturn(new GoalResponse(Arrays.asList(new Goal(GOAL_ID, GOAL_NAME))));


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
    public void makeUserVisitReportByRegressionData() {
        VisitsDuringMonthReportDto visitsDuringMonthReport = extractReportWithType(VisitsDuringMonthReportDto.class);

        List<Integer> metrics = Arrays.asList(
                33, 27, 29, 29, 22, 22, 22, 20, 32, 36, 33, 30, 12, 15, 24, 23, 18, 29, 26, 31, 25, 21, 29, 39, 33, 40, 15, 18, 20, 24
        );
        assertThat(visitsDuringMonthReport.getVisit(), is(equalTo(777)));
        assertThat(visitsDuringMonthReport.getVisitChange(), is(equalTo(-12)));
        assertThat(visitsDuringMonthReport.getAction(), is(equalTo(ActionEnum.DECREASING)));
        assertThat(visitsDuringMonthReport.getPercent(), is(closeTo(-1.52, 0.01)));
        assertThat(visitsDuringMonthReport.getMetrics(), is(equalTo(ReportUtil.orderedVisitsList(metrics, LocalDate.of(2016, 9, 1)))));
    }

    @Test
    public void makeReferringSourceReportByRegressionDataWithGoals() {
        ReferringSourceReport referringSourceReport = extractReportWithType(ReferringSourceReport.class);

        assertThat(referringSourceReport.getSourcesWithGoals(), hasSize(1));

        String sourceName = "Переходы из поисковых систем";
        Optional<ReferringSource> expectedSources = referringSourceReport.getSources().stream().filter(referringSource -> referringSource.getName().equals(sourceName)).findFirst();
        assertTrue(String.format("Expected: \"%s\" source not found.", sourceName), expectedSources.isPresent());
        assertThat(expectedSources.get().getTotalVisits(), is(equalTo(2123)));
        assertThat(expectedSources.get().getTotalVisitsChangePercent(), is(equalTo(-31.6)));
        assertThat(expectedSources.get().getTotalGoalVisits(), is(equalTo(10)));
        assertThat(expectedSources.get().getTotalVisitsChangePercent(), is(equalTo(60)));
        assertThat(expectedSources.get().getConversion(), is(equalTo(0.47)));
        assertThat(expectedSources.get().getConversionChangePercent(), is(equalTo(-21.6)));


        System.out.println(expectedSources.get().getTotalGoalVisits());

    }
    
    @Test
    public void directSearchPhrase() {
        ReportApiServiceTestHelper reportApiServiceTestHelper = new ReportApiServiceTestHelper(null);
        List<PhraseRow> allBy = reportApiServiceTestHelper.findAllBy(new DirectSearchPhrasesWithGoalsRequest(null, null, LocalDate.now(), LocalDate.now(), null));
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
        public <R> R findAllBy(CustomRequest<String, R> request) {
            String row = null;
            if (request instanceof DirectSearchPhrasesWithGoalsRequest) {
                try {
                    row = FileUtils.readFileToString(new File("src/test/resources/dataset/directSearchPhrase.json"), "UTF-8");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return request.transform(row);
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
