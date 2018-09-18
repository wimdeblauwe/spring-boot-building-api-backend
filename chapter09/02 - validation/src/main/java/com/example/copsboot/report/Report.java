package com.example.copsboot.report;

import com.example.copsboot.user.User;
import com.example.orm.jpa.AbstractEntity;
import com.example.util.ArtifactForFramework;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;
//tag::class[]
@Entity
public class Report extends AbstractEntity<ReportId> {
    @ManyToOne
    private User reporter;
    private ZonedDateTime dateTime;
    private String description;

    @ArtifactForFramework
    protected Report() {
    }

    public Report(ReportId id, User reporter, ZonedDateTime dateTime, String description) {
        super(id);
        this.reporter = reporter;
        this.dateTime = dateTime;
        this.description = description;
    }

    public User getReporter() {
        return reporter;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }
}
//end::class[]
