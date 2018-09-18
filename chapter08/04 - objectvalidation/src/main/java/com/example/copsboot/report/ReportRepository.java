package com.example.copsboot.report;

import com.example.copsboot.user.UserRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, ReportId>, ReportRepositoryCustom {
}
