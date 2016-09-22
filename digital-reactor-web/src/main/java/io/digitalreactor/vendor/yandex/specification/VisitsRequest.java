package io.digitalreactor.vendor.yandex.specification;


import io.digitalreactor.vendor.yandex.domain.Request;

import java.time.LocalDate;

/**
 * Created by MStepachev on 22.09.2016.
 */
public class VisitsRequest implements Request {

    private final String QUERY_TEMPLATE = "stat/v1/data/bytime?group=day&ids=%s&metrics=ym:s:visits&date1=%s&date2=%s&oauth_token=%s";

    private final String oauthToken;
    private final String counterId;
    private final String endIntervalDate;
    private final String startIntervalDate;

    public VisitsRequest(String oauthToken, String counterId, String endIntervalDate) {
        this.oauthToken = oauthToken;
        this.counterId = counterId;
        this.endIntervalDate = endIntervalDate;
        this.startIntervalDate = LocalDate.parse(endIntervalDate).minusMonths(1).toString();
    }

    @Override
    public String toQuery() {
        return String.format(QUERY_TEMPLATE, counterId, startIntervalDate, endIntervalDate, oauthToken);
    }
}
