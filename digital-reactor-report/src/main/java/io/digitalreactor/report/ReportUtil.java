package io.digitalreactor.report;

import io.digitalreactor.report.model.TwoMonthInterval;
import io.digitalreactor.web.contract.dto.report.VisitDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MStepachev on 28.09.2016.
 */
public class ReportUtil {

    public static double changPercent(List<Integer> first, List<Integer> second) {
        return (((double) sum(second) / (double)sum(first)) - 1.0) * 100.0;
    }

    public static int sum(List<Integer> metrics) {
        return metrics.stream().mapToInt(Integer::intValue).sum();
    }

    public static List<Integer> toIntegerList(List<Double> metrics) {
        return metrics.stream().map(Double::intValue).collect(Collectors.toList());
    }

    public static <T> List<T> getFirstMonthMetrics(TwoMonthInterval interval, List<T> metrics) {
        return new ArrayList<>(metrics.subList(0, interval.first().lengthOfMonth() - 1));
    }

    public static <T> List<T> getSecondMonthMetrics(TwoMonthInterval interval, List<T> metrics) {
        return new ArrayList<>(metrics.subList(interval.first().lengthOfMonth(), metrics.size() - 1));
    }

    public static List<VisitDto> visitsListWithDay(List<Integer> visits, LocalDate startTime) {

        List<VisitDto> result = new ArrayList<VisitDto>();
        LocalDate pointerDate = startTime;

        for (int visit : visits) {
            result.add(new VisitDto(
                    visit,
                    pointerDate.toString(),
                    isHoliday(pointerDate) ? VisitDto.DayType.HOLIDAY : VisitDto.DayType.WEEKDAY
            ));
            pointerDate = pointerDate.plusDays(1);
        }

        return result;
    }

    private static boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
