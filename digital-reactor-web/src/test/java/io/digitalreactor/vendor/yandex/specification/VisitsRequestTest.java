package io.digitalreactor.vendor.yandex.specification;

import io.digitalreactor.vendor.yandex.YandexServiceConfig;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * Created by MStepachev on 22.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = YandexServiceConfig.class)
public class VisitsRequestTest {
    @Autowired
    private ReportApiService reportApiService;

    @Test
    public void itIsExampleHowToCatchData() {
        Response visitsMetrics = reportApiService.findAllBy(new VisitsRequest(
                "AQAAAAAUQvf-AAK_6WmdQtnlGUIekdWX2d6RnU8",
                "31424723",
                LocalDate.now().toString()
        ));
    }
}