package com.example.copsboot.report.web;

import com.example.copsboot.report.CreateReportParameters;
import com.example.copsboot.user.UserId;

import java.time.Instant;

public record CreateReportRequest(Instant dateTime, String description) {
    public CreateReportParameters toParameters(UserId userId) {
        return new CreateReportParameters(userId, dateTime, description);
    }
}
