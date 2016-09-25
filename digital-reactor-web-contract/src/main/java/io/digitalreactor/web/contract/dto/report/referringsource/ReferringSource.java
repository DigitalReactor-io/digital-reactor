package io.digitalreactor.web.contract.dto.report.referringsource;

import io.digitalreactor.web.contract.dto.report.VisitDto;

import java.util.List;

/**
 * Created by ingvard on 24.09.16.
 */
public class ReferringSource {
    private String name;
    private List<VisitDto> metrics;
    private int totalVisits;
    private double totalVisitsChangePercent;
    private int totalGoalVisits;
    private double totalGoalVisitsChangePercent;
    private double conversion;
    private double conversionChangePercent;

    public ReferringSource(
            String name,
            List<VisitDto> metrics,
            int totalVisits,
            double totalVisitsChangePercent,
            int totalGoalVisits,
            double totalGoalVisitsChangePercent,
            double conversion,
            double conversionChangePercent
    ) {
        this.name = name;
        this.metrics = metrics;
        this.totalVisits = totalVisits;
        this.totalVisitsChangePercent = totalVisitsChangePercent;
        this.totalGoalVisits = totalGoalVisits;
        this.totalGoalVisitsChangePercent = totalGoalVisitsChangePercent;
        this.conversion = conversion;
        this.conversionChangePercent = conversionChangePercent;
    }

    public String getName() {
        return name;
    }

    public List<VisitDto> getMetrics() {
        return metrics;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public double getTotalVisitsChangePercent() {
        return totalVisitsChangePercent;
    }

    public int getTotalGoalVisits() {
        return totalGoalVisits;
    }

    public double getTotalGoalVisitsChangePercent() {
        return totalGoalVisitsChangePercent;
    }

    public double getConversion() {
        return conversion;
    }

    public double getConversionChangePercent() {
        return conversionChangePercent;
    }
}
