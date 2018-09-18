package com.example.copsboot.report;

import com.example.copsboot.user.UserId;

import java.time.ZonedDateTime;

public interface ReportService {
    Report createReport(UserId reporterId, ZonedDateTime dateTime, String description);
}
