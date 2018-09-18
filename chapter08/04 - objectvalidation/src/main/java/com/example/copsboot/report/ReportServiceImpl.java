package com.example.copsboot.report;

import com.example.copsboot.user.UserId;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    private final ReportRepository repository;
    private final UserService userService;

    public ReportServiceImpl(ReportRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Report createReport(UserId reporterId, ZonedDateTime dateTime, String description) {
        return repository.save(new Report(repository.nextId(),
                                          userService.getUser(reporterId)
                                                     .orElseThrow(() -> new UserNotFoundException(reporterId)),
                                          dateTime,
                                          description));
    }
}
