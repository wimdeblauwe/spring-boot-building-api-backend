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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//tag::class[]
@RunWith(SpringRunner.class)
@CopsbootControllerTest(ReportRestController.class)
public class ReportRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReportService service;

    @Test
    public void officerIsAbleToPostAReport() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "The suspect is wearing a black hat.";
        MockMultipartFile image = createMockImage();
        when(service.createReport(eq(Users.officer().getId()),
                                  any(ZonedDateTime.class),
                                  eq(description),
                                  any(MockMultipartFile.class)))
                .thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), ZonedDateTime.parse(dateTime), description));

        mvc.perform(fileUpload("/api/reports") //<1>
                            .file(image) //<2>
                            .header(HEADER_AUTHORIZATION, bearer(accessToken))
                            .param("dateTime", dateTime) //<3>
                            .param("description", description))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("id").exists())
           .andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
           .andExpect(jsonPath("dateTime").value(dateTime))
           .andExpect(jsonPath("description").value(description));
    }

    private MockMultipartFile createMockImage() { //<4>
        return new MockMultipartFile("image",
                                     "picture.png",
                                     "image/png",
                                     new byte[10_000_000]);
    }
}
//end::class[]