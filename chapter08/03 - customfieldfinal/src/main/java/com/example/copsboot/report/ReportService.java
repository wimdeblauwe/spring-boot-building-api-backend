package com.example.copsboot.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
public class ReportService {
    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report createReport(CreateReportParameters parameters) {
        return repository.save(new Report(repository.nextId(),
                parameters.userId(),
                parameters.dateTime(),
                parameters.description()));
    }
}
