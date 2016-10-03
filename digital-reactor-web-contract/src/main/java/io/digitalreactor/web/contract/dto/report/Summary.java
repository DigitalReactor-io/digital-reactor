package io.digitalreactor.web.contract.dto.report;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by MStepachev on 11.05.2016.
 */
@Document(collection = "summaries")
public class Summary {

    private String taskId;
    private List<Object> reports;

    public Summary(String taskId, List<Object> reports) {
        this.taskId = taskId;
        this.reports = reports;
    }

    public List<Object> getReports() {
        return reports;
    }

    public String getTaskId() {
        return taskId;
    }
}
