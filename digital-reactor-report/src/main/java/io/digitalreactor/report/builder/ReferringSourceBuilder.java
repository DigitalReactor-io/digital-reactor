package io.digitalreactor.report.builder;

import io.digitalreactor.web.contract.dto.report.VisitDto;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSource;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSourceReport;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ingvard on 13.09.16.
 */
public class ReferringSourceBuilder {
    public static class ReferringSourceRow {
        private LocalDate lastFullDay;
    }

    public static ReferringSource referringSource(String name, List<VisitDto> metrics, List<VisitDto> goalMetrics) {

        int totalVisits = 0;
        double totalVisitsChangePercent = 0;
        int totalGoalVisits = 0;
        double totalGoalVisitsChangePercent = 0;
        double conversion = 0;
        double conversionChangePercent = 0;

        ReferringSource referringSource = new ReferringSource(name, metrics, );

        return null;
    }

    public ReferringSourceReport build(ReferringSourceRow referringSourceRow) {
        //ReferringSourceReport referringSourceReportDto = new ReferringSourceReport();


        return null;
    }
}
