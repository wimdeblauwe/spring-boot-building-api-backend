package com.example.copsboot.report.web;

import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportId;
import com.example.copsboot.user.UserService;

import java.time.Instant;

//tag::class[]
public record ReportDto(ReportId id,
                        String reporter,
                        Instant dateTime,
                        String description) {

    public static ReportDto fromReport(Report report, UserService userService) {
        return new ReportDto(report.getId(),
                userService.getUserById(report.getReporterId()).getEmail(),
                report.getDateTime(),
                report.getDescription());
    }
}
//end::class[]
