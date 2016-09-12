package io.digitalreactor.web.contract.dto;

import java.time.LocalDate;

/**
 * Created by MStepachev on 12.09.2016.
 */
public class SummaryStatusUI {
    private SummaryStatusEnum status;
    private LocalDate date;

    public SummaryStatusUI(SummaryStatusEnum status, LocalDate date) {
        this.status = status;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public SummaryStatusEnum getStatus() {
        return status;
    }
}