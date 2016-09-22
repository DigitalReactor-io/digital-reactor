package io.digitalreactor.report.builder;

import io.digitalreactor.web.contract.dto.report.ActionEnum;
import io.digitalreactor.web.contract.dto.report.VisitsDuringMonthReportDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by ingvard on 13.09.16.
 */
public class VisitsDuringMothReportBuilder {
    private final int TWO_WEEK = 14;

    public VisitsDuringMonthReportDto build() {
        JsonObject object = new JsonObject(reportMessage.raw);
        int total = ((Double) ((List) object.getJsonArray("totals").getList().get(0)).get(0)).intValue();
        List<Double> metrics = (List) object.getJsonArray("data").getJsonObject(0).getJsonArray("metrics").getList().get(0);
        String date = object.getJsonObject("query").getString("date1");
        String date2 = object.getJsonObject("query").getString("date2");
        List<Integer> visits = metrics.stream().map(aDouble -> aDouble.intValue()).collect(Collectors.toList());

        List<List<Double>> twoCalendarWeek = chooseValueOfWeekAndLastWeek(metrics, LocalDate.parse(date2));
        ActionEnum action = ActionEnum.INSUFFICIENT_DATA;
        int trendChangePercent = 0;

        if (!twoCalendarWeek.isEmpty()) {
            double sumWeek1 = twoCalendarWeek.get(0).stream().mapToDouble(x -> x).sum();
            double sumWeek2 = twoCalendarWeek.get(1).stream().mapToDouble(x -> x).sum();
            double delta = sumWeek1 - sumWeek2;
            action = trendChanges(delta);

            if (sumWeek2 > sumWeek1) {
                trendChangePercent = (int) ((((double) sumWeek2 / (double) sumWeek1) - 1.0) * 100);
            }

            if (sumWeek2 < sumWeek1) {
                trendChangePercent = (int) ((((double) sumWeek1 / (double) sumWeek2) - 1.0) * 100);
            }
        }

        return new VisitsDuringMonthReportDto(
                total, trendChangePercent, action,
                VisitsDuringMonthReportDto.visitsListWithDay(visits, LocalDate.parse(date)),
                "Текст для причины"
        );
    }

    public List<List<Double>> chooseValueOfWeekAndLastWeek(List<Double> metrics, LocalDate endReportDate) {

        List<List<Double>> valuesForTwoCalendarWeeks = new ArrayList<>();

        LocalDate endOfLastWeek = endReportDate.minus(1, ChronoUnit.WEEKS).with(DayOfWeek.SUNDAY);
        int rightShift = (int) DAYS.between(endOfLastWeek, endReportDate);

        if (metrics.size() - TWO_WEEK - rightShift >= 0) {
            valuesForTwoCalendarWeeks.add(metrics.subList(
                    metrics.size() - rightShift - 14,
                    metrics.size() - rightShift - 7
            ));
            valuesForTwoCalendarWeeks.add(metrics.subList(
                    metrics.size() - rightShift - 7,
                    metrics.size() - rightShift
            ));
        }

        return valuesForTwoCalendarWeeks;
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
