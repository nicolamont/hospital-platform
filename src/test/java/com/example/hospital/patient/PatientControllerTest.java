package com.example.hospital.patient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/** Test HTTP del controller REST dei pazienti. */
@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

  @Mock private PatientService patientService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();
    mockMvc =
        MockMvcBuilders.standaloneSetup(new PatientController(patientService))
            .setValidator(validator)
            .build();
  }

  @Test
  void shouldReturnAllPatients() throws Exception {
    when(patientService.findAll()).thenReturn(List.of(createPatient()));

    mockMvc
        .perform(get("/api/v1/patients"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value("Mario"))
        .andExpect(jsonPath("$[0].lastName").value("Rossi"));
  }

  @Test
  void shouldReturnPatientById() throws Exception {
    when(patientService.findById(1L)).thenReturn(createPatient());

    mockMvc
        .perform(get("/api/v1/patients/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("Mario"))
        .andExpect(jsonPath("$.taxCode").value("RSSMRA85D12H501X"));
  }

  @Test
  void shouldReturnNotFoundWhenPatientDoesNotExist() throws Exception {
    when(patientService.findById(99L)).thenThrow(new PatientNotFoundException(99L));

    mockMvc.perform(get("/api/v1/patients/99")).andExpect(status().isNotFound());
  }

  @Test
  void shouldCreatePatientWithHttpCreated() throws Exception {
    when(patientService.create(any(Patient.class))).thenReturn(createPatient());

    mockMvc
        .perform(
            post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validPatientJson()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName").value("Mario"));
  }

  @Test
  void shouldRejectInvalidPatientRequest() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "firstName": "",
                      "lastName": "",
                      "birthDate": null,
                      "taxCode": ""
                    }
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldUpdatePatient() throws Exception {
    when(patientService.update(any(Long.class), any(Patient.class))).thenReturn(createPatient());

    mockMvc
        .perform(
            put("/api/v1/patients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validPatientJson()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("Mario"));
  }

  @Test
  void shouldDeletePatient() throws Exception {
    doNothing().when(patientService).delete(1L);

    mockMvc.perform(delete("/api/v1/patients/1")).andExpect(status().isNoContent());

    verify(patientService).delete(1L);
  }

  private Patient createPatient() {
    return new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
  }

  private String validPatientJson() {
    return """
        {
          "firstName": "Mario",
          "lastName": "Rossi",
          "birthDate": "1985-04-12",
          "taxCode": "RSSMRA85D12H501X"
        }
        """;
  }
}
