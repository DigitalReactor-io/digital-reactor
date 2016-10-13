package io.digitalreactor.web.contract.dto.report;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MStepachev on 11.05.2016.
 */
public class VisitsDuringMonthReportDto {
    private ReportTypeEnum type = ReportTypeEnum.VISITS_DURING_MONTH;
    private int visit;
    private int visitChange;
    private double percent;
    private ActionEnum action;
    private List<VisitDto> metrics;
    private String reason;

    public VisitsDuringMonthReportDto(int visit, int visitChange, double percent, ActionEnum action, List<VisitDto> metrics, String reason) {
        this.visit = visit;
        this.visitChange = visitChange;
        this.percent = percent;
        this.action = action;
        this.metrics = metrics;
        this.reason = reason;
    }

    public int getVisitChange() {
        return visitChange;
    }

    public ReportTypeEnum getType() {
        return type;
    }

    public int getVisit() {
        return visit;
    }

    public double getPercent() {
        return percent;
    }

    public ActionEnum getAction() {
        return action;
    }

    public List<VisitDto> getMetrics() {
        return metrics;
    }

    public String getReason() {
        return reason;
    }
}
