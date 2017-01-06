package io.digitalreactor.web.contract.dto.report.direct;

/**
 * Created by ingvard on 05.01.17.
 */
public class DirectSearchPhraseDto {

    private String searchPhrase;

    //ym:s:visits
    private int visits;

    //ym:s:bounceRate
    private int bounceRate;

    //ym:s:pageDepth
    private int pageDepth;

    //ym:s:avgVisitDurationSeconds
    private int avgVisitDurationSeconds;

    //sometime it is a conversion.
    private double quality;

    public DirectSearchPhraseDto(String searchPhrase, int visits, int bounceRate, int pageDepth, int avgVisitDurationSeconds, double quality) {
        this.searchPhrase = searchPhrase;
        this.visits = visits;
        this.bounceRate = bounceRate;
        this.pageDepth = pageDepth;
        this.avgVisitDurationSeconds = avgVisitDurationSeconds;
        this.quality = quality;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public int getVisits() {
        return visits;
    }

    public int getBounceRate() {
        return bounceRate;
    }

    public int getPageDepth() {
        return pageDepth;
    }

    public int getAvgVisitDurationSeconds() {
        return avgVisitDurationSeconds;
    }

    public double getQuality() {
        return quality;
    }
}
