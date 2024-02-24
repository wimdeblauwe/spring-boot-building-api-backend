package com.example.copsboot.report.web;

import com.example.copsboot.report.CreateReportParameters;
import com.example.copsboot.user.UserId;

import java.time.Instant;

@ValidCreateReportRequest
public record CreateReportRequest(Instant dateTime, @ValidReportDescription String description,
                                  boolean trafficIncident, int numberOfInvolvedCars) {
    public CreateReportParameters toParameters(UserId userId) {
        return new CreateReportParameters(userId, dateTime, description);
    }
}
