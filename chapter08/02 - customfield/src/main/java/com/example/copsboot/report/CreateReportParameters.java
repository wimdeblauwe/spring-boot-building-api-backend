package com.example.copsboot.report;

import com.example.copsboot.user.UserId;

import java.time.Instant;

public record CreateReportParameters(UserId userId, Instant dateTime, String description) {
}
