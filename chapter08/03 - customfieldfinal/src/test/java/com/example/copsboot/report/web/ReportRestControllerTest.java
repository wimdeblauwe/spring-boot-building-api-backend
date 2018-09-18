package com.example.copsboot.report.web;

import com.example.copsboot.infrastructure.test.CopsbootControllerTest;
import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportId;
import com.example.copsboot.report.ReportService;
import com.example.copsboot.user.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//tag::class[]
@RunWith(SpringRunner.class)
@CopsbootControllerTest(ReportRestController.class)
public class ReportRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReportService service;

    @Test
    public void officerIsAbleToPostAReport() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        ZonedDateTime dateTime = ZonedDateTime.parse("2018-04-11T22:59:03.189+02:00");
        String description = "The suspect is wearing a black hat.";
        CreateReportParameters parameters = new CreateReportParameters(dateTime,
                                                                       description);
        when(service.createReport(eq(Users.officer().getId()), any(ZonedDateTime.class), eq(description)))
                .thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), dateTime, description));

        mvc.perform(post("/api/reports")
                            .header(HEADER_AUTHORIZATION, bearer(accessToken))
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(parameters)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("id").exists())
           .andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
           .andExpect(jsonPath("dateTime").value("2018-04-11T22:59:03.189+02:00"))
           .andExpect(jsonPath("description").value(description));
    }
}
//end::class[]