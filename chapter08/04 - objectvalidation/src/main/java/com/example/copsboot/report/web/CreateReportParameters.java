package com.example.copsboot.report.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

//tag::class[]
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidCreateReportParameters
public class CreateReportParameters {
    private ZonedDateTime dateTime;

    @ValidReportDescription
    private String description;

    private boolean trafficIncident;
    private int numberOfInvolvedCars;
}
//end::class[]