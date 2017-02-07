package io.digitalreactor.vendor.yandex.model;

import java.util.Map;

/**
 * Created by ingvard on 06.01.17.
 */
public class PhraseRow {
    private String searchPhrase;

    //ym:s:visits
    private int visits;

    //ym:s:bounceRate
    private double bounceRate;

    //ym:s:pageDepth
    private double pageDepth;

    //ym:s:avgVisitDurationSeconds
    private double avgVisitDurationSeconds;

    private Map<String, Integer> goalAndConversion;

    public PhraseRow(String searchPhrase, int visits, double bounceRate, double pageDepth, double avgVisitDurationSeconds, Map<String, Integer> goalAndConversion) {
        this.searchPhrase = searchPhrase;
        this.visits = visits;
        this.bounceRate = bounceRate;
        this.pageDepth = pageDepth;
        this.avgVisitDurationSeconds = avgVisitDurationSeconds;
        this.goalAndConversion = goalAndConversion;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public int getVisits() {
        return visits;
    }

    public double getBounceRate() {
        return bounceRate;
    }

    public double getPageDepth() {
        return pageDepth;
    }

    public double getAvgVisitDurationSeconds() {
        return avgVisitDurationSeconds;
    }

    public Map<String, Integer> getGoalAndConversion() {
        return goalAndConversion;
    }
}
