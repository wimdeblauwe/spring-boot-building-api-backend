package com.example.copsboot.report;

import com.example.copsboot.user.UserId;
import com.example.orm.jpa.AbstractEntity;
import com.example.util.ArtifactForFramework;
import jakarta.persistence.Entity;

import java.time.Instant;

//tag::class[]
@Entity
public class Report extends AbstractEntity<ReportId> {

    private UserId reporterId;
    private Instant dateTime;
    private String description;

    @ArtifactForFramework
    protected Report() {
    }

    public Report(ReportId id, UserId reporterId, Instant dateTime, String description) {
        super(id);
        this.reporterId = reporterId;
        this.dateTime = dateTime;
        this.description = description;
    }

    public UserId getReporterId() {
        return reporterId;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }
}
//end::class[]
