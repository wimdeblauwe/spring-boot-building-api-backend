package com.example.copsboot.report;

import com.example.copsboot.user.Users;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Reports {
    public static Report createRandomReport(String description) {
        return new Report(new ReportId(UUID.randomUUID()),
                          Users.newRandomOfficer(),
                          ZonedDateTime.now(),
                          description);
    }
}
