package io.digitalreactor.web.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by MStepachev on 12.09.2016.
 */

public class SummaryStatusUI {
    private String status;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    private Date date;
    private String taskId;

    public SummaryStatusUI(String status, Date date, String taskId) {
        this.status = status;
        this.date = date;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
