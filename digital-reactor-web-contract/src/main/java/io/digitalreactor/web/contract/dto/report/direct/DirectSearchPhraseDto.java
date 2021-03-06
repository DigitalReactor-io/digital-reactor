package io.digitalreactor.web.contract.dto.report.direct;

/**
 * Created by ingvard on 05.01.17.
 */
public class DirectSearchPhraseDto {

    private String searchPhrase;

    //ym:s:visits
    private int visits;

    //ym:s:bounceRate
    private double bounceRate;

    //ym:s:pageDepth
    private double pageDepth;

    //ym:s:avgVisitDurationSeconds
    private double avgVisitDurationSeconds;

    //sometime it is a conversion.
    private double quality;

    public DirectSearchPhraseDto(String searchPhrase, int visits, double bounceRate, double pageDepth, double avgVisitDurationSeconds, double quality) {
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

    public double getBounceRate() {
        return bounceRate;
    }

    public double getPageDepth() {
        return pageDepth;
    }

    public double getAvgVisitDurationSeconds() {
        return avgVisitDurationSeconds;
    }

    public double getQuality() {
        return quality;
    }
}
