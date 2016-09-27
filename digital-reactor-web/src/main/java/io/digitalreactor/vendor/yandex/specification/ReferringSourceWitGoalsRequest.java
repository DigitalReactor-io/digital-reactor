package io.digitalreactor.vendor.yandex.specification;

import io.digitalreactor.vendor.yandex.domain.Request;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MStepachev on 27.09.2016.
 */
public class ReferringSourceWitGoalsRequest implements Request {
    private final String QUERY_TEMPLATE = "stat/v1/data/bytime?group=day&id=%s&metrics=ym:s:visits%s&date1=%s&date2=%s&dimensions=ym:s:lastTrafficSource&oauth_token=%s";
    private final String GOAL_PARAMETER_TEMPLATE = "ym:s:goal%svisits";

    private final String oauthToken;
    private final String counterId;
    private final String endIntervalDate;
    private final String startIntervalDate;
    private final List<String> goalsIds;

    public ReferringSourceWitGoalsRequest(String oauthToken, String counterId, List<String> goalsIds, String endIntervalDate) {
        this.oauthToken = oauthToken;
        this.counterId = counterId;
        this.goalsIds = goalsIds;
        this.endIntervalDate = endIntervalDate;
        this.startIntervalDate = LocalDate.parse(endIntervalDate).minusMonths(2).withDayOfMonth(1).toString();
    }

    private String makeListOfGoals(List<String> goalsIds) {
        return goalsIds.stream()
                .map(goalId -> String.format(GOAL_PARAMETER_TEMPLATE, goalId))
                .collect(Collectors.joining(","));
    }

    @Override
    public String toQuery() {
        String additionMetrics = "";

        if (goalsIds != null) {
            additionMetrics = "," + makeListOfGoals(goalsIds);
        }

        return String.format(QUERY_TEMPLATE, counterId, additionMetrics, startIntervalDate, endIntervalDate, oauthToken);
    }
}
