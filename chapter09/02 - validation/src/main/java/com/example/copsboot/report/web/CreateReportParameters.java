package com.example.copsboot.report.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

//tag::class[]
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportParameters {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateTime;

    @ValidReportDescription
    private String description;

    @NotNull
    private MultipartFile image; //<1>
}
//end::class[]