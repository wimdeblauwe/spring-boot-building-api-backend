package com.example.copsboot.report.web;

import com.example.copsboot.report.CreateReportParameters;
import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportService;
import com.example.copsboot.user.AuthServerId;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//tag::class[]
@RestController
@RequestMapping("/api/reports")
public class ReportRestController {
    private final ReportService service;
    private final UserService userService;

    public ReportRestController(ReportService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    // tag::create-report-method-signature[]
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createReport(@AuthenticationPrincipal Jwt jwt,
                                  @Valid CreateReportRequest request) {
        // end::create-report-method-signature[]
        AuthServerId authServerId = new AuthServerId(UUID.fromString(jwt.getSubject()));
        User user = userService.findUserByAuthServerId(authServerId)
                .orElseThrow(() -> new UserNotFoundException(authServerId));
        CreateReportParameters parameters = request.toParameters(user.getId());
        Report report = service.createReport(parameters);
        return ReportDto.fromReport(report, userService);
    }
}
//end::class[]
