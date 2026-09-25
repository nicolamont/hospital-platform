package com.example.hospital.examresult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/** Test HTTP del controller REST degli esiti degli esami. */
@ExtendWith(MockitoExtension.class)
class ExamResultControllerTest {

  @Mock private ExamResultService examResultService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();
    mockMvc =
        MockMvcBuilders.standaloneSetup(new ExamResultController(examResultService))
            .setValidator(validator)
            .build();
  }

  @Test
  void shouldCreateExamResultWithHttpCreated() throws Exception {
    ExamResultResponse response =
        new ExamResultResponse(
            1L, 1L, "Esame del sangue", "Valori nella norma", LocalDate.of(2026, 9, 25));
    when(examResultService.create(any(CreateExamResultRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/exam-results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "medicalRecordId": 1,
                      "examType": "Esame del sangue",
                      "result": "Valori nella norma",
                      "performedAt": "2026-09-25"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.medicalRecordId").value(1))
        .andExpect(jsonPath("$.examType").value("Esame del sangue"));
  }

  @Test
  void shouldRejectInvalidExamResultRequest() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/exam-results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "medicalRecordId": null,
                      "examType": "",
                      "result": "",
                      "performedAt": null
                    }
                    """))
        .andExpect(status().isBadRequest());
  }
}
