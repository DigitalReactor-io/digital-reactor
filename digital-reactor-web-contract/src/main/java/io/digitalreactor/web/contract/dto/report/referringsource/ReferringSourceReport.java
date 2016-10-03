package io.digitalreactor.web.contract.dto.report.referringsource;

import io.digitalreactor.web.contract.dto.report.ReportTypeEnum;

import java.util.List;

/**
 * Created by ingvard on 24.09.16.
 */
public class ReferringSourceReport {
    private ReportTypeEnum type = ReportTypeEnum.REFERRING_SOURCE;
    private List<ReferringSource> sources;
    private List<GoalReferringSources> sourcesWithGoals;

    public ReferringSourceReport(List<ReferringSource> sources, List<GoalReferringSources> sourcesWithGoals) {
        this.sources = sources;
        this.sourcesWithGoals = sourcesWithGoals;
    }

    public ReportTypeEnum getType() {
        return type;
    }

    public List<ReferringSource> getSources() {
        return sources;
    }

    public List<GoalReferringSources> getSourcesWithGoals() {
        return sourcesWithGoals;
    }
}
