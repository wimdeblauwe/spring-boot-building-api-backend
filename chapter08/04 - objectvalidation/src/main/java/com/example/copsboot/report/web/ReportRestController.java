package com.example.copsboot.report.web;

import com.example.copsboot.infrastructure.security.ApplicationUserDetails;
import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//tag::class[]
@RestController
@RequestMapping("/api/reports")
public class ReportRestController {
    private final ReportService service;

    public ReportRestController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createReport(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                               @Valid @RequestBody CreateReportParameters parameters) {
        return ReportDto.fromReport(service.createReport(userDetails.getUserId(),
                                    parameters.getDateTime(),
                                    parameters.getDescription()));
    }
}
//end::class[]
