package io.digitalreactor.web.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Created by MStepachev on 12.09.2016.
 */

public class SummaryStatusUI {
    private String status;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;
    private String taskId;

    public SummaryStatusUI(String status, LocalDate date, String taskId) {
        this.status = status;
        this.date = date;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
