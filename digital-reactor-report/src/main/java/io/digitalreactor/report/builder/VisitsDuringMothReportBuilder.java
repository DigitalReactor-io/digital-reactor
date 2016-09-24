package io.digitalreactor.report.builder;

import io.digitalreactor.web.contract.dto.report.ActionEnum;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ingvard on 13.09.16.
 */
public class VisitsDuringMothReportBuilder {
    private final int TWO_WEEK = 14;

    public static class VisitsDuringMonthRowData {
        List<Double> current30Days;
        List<Double> monthAgo;
        List<Double> twoMonthAgo;
        LocalDate lastFullDay;

        public VisitsDuringMonthRowData(List<Double> current30Days, List<Double> monthAgo, List<Double> twoMonthAgo, LocalDate lastFullDay) {
            this.current30Days = current30Days;
            this.monthAgo = monthAgo;
            this.twoMonthAgo = twoMonthAgo;
            this.lastFullDay = lastFullDay;
        }

        public List<Double> getCurrent30Days() {
            return current30Days;
        }

        public List<Double> getMonthAgo() {
            return monthAgo;
        }

        public List<Double> getTwoMonthAgo() {
            return twoMonthAgo;
        }

        public LocalDate getLastFullDay() {
            return lastFullDay;
        }
    }

    public VisitsDuringMonthReportDto build(VisitsDuringMonthRowData rowData) {

        Double sumVisitMonthAgo = rowData.getMonthAgo().stream().mapToDouble(Double::doubleValue).sum();
        Double sumVisitTwoMonthAgo = rowData.getTwoMonthAgo().stream().mapToDouble(Double::doubleValue).sum();


        ActionEnum action = ActionEnum.UNALTERED;
        int trendChangePercent = 0;
        int total = 0;

        double delta = sumVisitMonthAgo - sumVisitTwoMonthAgo;
        action = trendChanges(delta);

        if (sumVisitTwoMonthAgo > sumVisitMonthAgo) {
            trendChangePercent = (int) (((sumVisitTwoMonthAgo / sumVisitTwoMonthAgo) - 1.0) * 100);
            total = (int) (sumVisitTwoMonthAgo - sumVisitMonthAgo);
        }

        if (sumVisitTwoMonthAgo < sumVisitMonthAgo) {
            trendChangePercent = (int) (((sumVisitMonthAgo / sumVisitTwoMonthAgo) - 1.0) * 100);
            total = (int) (sumVisitMonthAgo - sumVisitTwoMonthAgo);
        }

        return new VisitsDuringMonthReportDto(
                total, trendChangePercent, action,
                VisitsDuringMonthReportDto.visitsListWithDay(
                        rowData.getCurrent30Days().stream().map(Double::intValue).collect(Collectors.toList()),
                        rowData.getLastFullDay()
                ),
                "Текст для причины"
        );
    }

    private ActionEnum trendChanges(double delta) {
        ActionEnum action = ActionEnum.DECREASING;

        if (delta == 0) {
            action = ActionEnum.UNALTERED;
        } else if (delta > 0) {
            action = ActionEnum.INCREASING;
        }

        return action;
    }
}
