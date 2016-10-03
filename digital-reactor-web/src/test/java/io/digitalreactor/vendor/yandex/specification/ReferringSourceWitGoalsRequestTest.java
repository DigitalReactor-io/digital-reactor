package io.digitalreactor.vendor.yandex.specification;

import io.digitalreactor.vendor.yandex.YandexServiceConfig;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.GoalApiService;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * Created by MStepachev on 27.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = YandexServiceConfig.class)
public class ReferringSourceWitGoalsRequestTest {
    @Autowired
    private ReportApiService reportApiService;

    @Autowired
    private GoalApiService goalApiService;

    private final String TOKEN = "AQAAAAAUQvf-AAK_6WmdQtnlGUIekdWX2d6RnU8";
    private final String COUNTER_ID = "31424723";

    @Test
    public void itIsExampleHowToCatchData() {
        GoalResponse goals = goalApiService.getGoals(COUNTER_ID, TOKEN);

        Response visitsMetrics = reportApiService.findAllBy(new ReferringSourceWitGoalsRequest(
                TOKEN,
                COUNTER_ID,
                LocalDate.now().minusMonths(2).toString(),
                LocalDate.now().toString(),
                goals.getGoalsIds()
        ));


    }
}