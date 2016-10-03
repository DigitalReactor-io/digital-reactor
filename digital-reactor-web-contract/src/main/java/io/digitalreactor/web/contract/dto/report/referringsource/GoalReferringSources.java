package io.digitalreactor.web.contract.dto.report.referringsource;

import java.util.List;

/**
 * Created by ingvard on 24.09.16.
 */
public class GoalReferringSources {
    private String name;
    private List<ReferringSource> sources;
    private double conversion;
    private double conversionChange;
    private int numberOfCompletedGoal;

    public GoalReferringSources(String name, List<ReferringSource> sources, double conversion, double conversionChange, int numberOfCompletedGoal) {
        this.name = name;
        this.sources = sources;
        this.conversion = conversion;
        this.conversionChange = conversionChange;
        this.numberOfCompletedGoal = numberOfCompletedGoal;
    }

    public String getName() {
        return name;
    }

    public List<ReferringSource> getSources() {
        return sources;
    }

    public double getConversion() {
        return conversion;
    }

    public double getConversionChange() {
        return conversionChange;
    }

    public int getNumberOfCompletedGoal() {
        return numberOfCompletedGoal;
    }
}
