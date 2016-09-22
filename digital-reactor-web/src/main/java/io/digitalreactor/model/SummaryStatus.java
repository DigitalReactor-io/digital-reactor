package io.digitalreactor.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Created by MStepachev on 13.09.2016.
 */
@Document(collection = "summariesStatus")
public class SummaryStatus {
    private String accountId;
    private String siteId;
    private SummaryStatusEnum status;
    private LocalDate date;

    public SummaryStatus() {
    }

    public SummaryStatus(String accountId, String siteId, SummaryStatusEnum status, LocalDate date) {
        this.accountId = accountId;
        this.siteId = siteId;
        this.status = status;
        this.date = date;
    }

    public void setStatus(SummaryStatusEnum status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSiteId() {
        return siteId;
    }

    public SummaryStatusEnum getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }
}
