package com.example.copsboot.report.web;

import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportId;
import lombok.Value;

import java.time.ZonedDateTime;

//tag::class[]
@Value
public class ReportDto {
    private ReportId id;
    private String reporter;
    private ZonedDateTime dateTime;
    private String description;

    public static ReportDto fromReport(Report report) {
        return new ReportDto(report.getId(),
                             report.getReporter().getEmail(),
                             report.getDateTime(),
                             report.getDescription());
    }
}
//end::class[]
