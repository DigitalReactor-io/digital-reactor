package io.digitalreactor.report.builder;

import io.digitalreactor.report.model.TwoMonthInterval;
import io.digitalreactor.vendor.yandex.model.Goal;
import io.digitalreactor.vendor.yandex.model.Response;
import io.digitalreactor.web.contract.dto.report.VisitDto;
import io.digitalreactor.web.contract.dto.report.referringsource.GoalReferringSources;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSource;
import io.digitalreactor.web.contract.dto.report.referringsource.ReferringSourceReport;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.digitalreactor.report.ReportUtil.*;

/**
 * Created by ingvard on 13.09.16.
 */
public class ReferringSourceBuilder {

    private static final int WITHOUT_GOAL = 0;

    //TODO[St.maxim] it is prototype
    public ReferringSourceReport build(TwoMonthInterval monthInterval, List<Goal> goals, List<Response.Data> datas) {
        List<ReferringSource> sources = sources(monthInterval, datas, WITHOUT_GOAL);
        List<GoalReferringSources> sourcesWithGoals = sourcesWithGoals(monthInterval, goals, datas);

        return new ReferringSourceReport(sources, sourcesWithGoals);
    }

    private List<GoalReferringSources> sourcesWithGoals(TwoMonthInterval monthInterval, List<Goal> goals, List<Response.Data> datas) {
        List<GoalReferringSources> goalReferringSources = new ArrayList<>();

        Pair<Integer, Integer> visitsWithoutGoal = fullSumForAllReferring(monthInterval, datas, WITHOUT_GOAL);

        int i = 1;
        for (Goal goal : goals) {

            List<ReferringSource> sources = sources(monthInterval, datas, i);

            Pair<Integer, Integer> visitsByGoal = fullSumForAllReferring(monthInterval, datas, i);

            double conversion = (((double) visitsByGoal.getSecond() / (double) visitsWithoutGoal.getSecond()) - 1.0) * 100.0;
            double conversionChange = ((((double) visitsByGoal.getSecond() / (double) visitsWithoutGoal.getSecond()) /
                    ((double) visitsByGoal.getFirst() / (double) visitsWithoutGoal.getFirst())) - 1.0) * 100.0;

            int numberOfCompletedGoal = sources.stream().mapToInt(ReferringSource::getTotalGoalVisits).sum();

            goalReferringSources.add(new GoalReferringSources(
                    goal.getName(),
                    sources,
                    conversion,
                    conversionChange,
                    numberOfCompletedGoal

            ));

            i++;
        }

        return goalReferringSources;
    }

    /**
     * Returns the conversion and conversionChange
     */
    private Pair<Integer, Integer> fullSumForAllReferring(TwoMonthInterval monthInterval, List<Response.Data> datas, int metricsIndex) {

        int visitsSumForSecondMonth = datas.stream().mapToInt(
                referring -> sum(toIntegerList(getSecondMonthMetrics(monthInterval, referring.getMetrics().get(metricsIndex))))
        ).sum();

        int visitsSumForFirstMonth = datas.stream().mapToInt(
                referring -> sum(toIntegerList(getFirstMonthMetrics(monthInterval, referring.getMetrics().get(metricsIndex))))
        ).sum();

        return Pair.of(visitsSumForFirstMonth, visitsSumForSecondMonth);
    }


    private List<ReferringSource> sources(TwoMonthInterval monthInterval, List<Response.Data> datas, int goalNumber) {
        return datas.stream()
                .map(row -> referringSource(monthInterval, row, goalNumber)).collect(Collectors.toList());
    }

    private ReferringSource referringSource(TwoMonthInterval monthInterval, Response.Data row, int goalNumber) {

        int totalGoalVisits = 0;
        double totalGoalVisitsChangePercent = 0;
        double conversion = 0;
        double conversionChangePercent = 0;

        String sourceName = row.getDimensions().get(0).get("name");
        List<Integer> rowMetricsData = toIntegerList(row.getMetrics().get(0));
        List<Integer> rowGoalMetricsData = null;

        List<Integer> firstMonthMetrics = getFirstMonthMetrics(monthInterval, rowMetricsData);
        List<Integer> secondMonthMetrics = getSecondMonthMetrics(monthInterval, rowMetricsData);

        List<VisitDto> metrics = visitsListWithDay(
                secondMonthMetrics,
                monthInterval.firstDayOfSecondMonth()
        );

        int totalVisits = secondMonthMetrics.stream().mapToInt(Integer::intValue).sum();
        double totalVisitsChangePercent = changPercent(firstMonthMetrics, secondMonthMetrics);


        if (goalNumber != WITHOUT_GOAL) {
            rowGoalMetricsData = toIntegerList(row.getMetrics().get(goalNumber));
            List<Integer> secondMonthGoalMetrics = getSecondMonthMetrics(monthInterval, rowGoalMetricsData);
            List<Integer> firstMonthGoalMetrics = getFirstMonthMetrics(monthInterval, rowGoalMetricsData);
            totalGoalVisits = sum(secondMonthGoalMetrics);
            totalGoalVisitsChangePercent = changPercent(firstMonthGoalMetrics, secondMonthGoalMetrics);
            conversion = (double) totalGoalVisits / (double) totalVisits;
            conversionChangePercent = ((conversion / ((double) sum(firstMonthGoalMetrics) / (double) sum(firstMonthMetrics))) - 1) * 100.0;
        }

        return new ReferringSource(
                sourceName,
                metrics,
                totalVisits,
                totalVisitsChangePercent,
                totalGoalVisits,
                totalGoalVisitsChangePercent,
                conversion,
                conversionChangePercent
        );
    }
}
