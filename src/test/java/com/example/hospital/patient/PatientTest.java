package com.example.hospital.patient;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Test unitari dell'entità Patient e della richiesta di creazione. */
class PatientTest {

  private static Validator validator;
  private static AutoCloseable validatorFactory;

  @BeforeAll
  static void setUpValidator() {
    var factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    validatorFactory = factory;
  }

  @AfterAll
  static void closeValidatorFactory() throws Exception {
    validatorFactory.close();
  }

  @Test
  void shouldCreatePatientAndExposeValuesThroughGetters() {
    LocalDate birthDate = LocalDate.of(1985, 4, 12);
    Patient patient = new Patient("Mario", "Rossi", birthDate, "RSSMRA85D12H501X");

    assertThat(patient.getId()).isNull();
    assertThat(patient.getFirstName()).isEqualTo("Mario");
    assertThat(patient.getLastName()).isEqualTo("Rossi");
    assertThat(patient.getBirthDate()).isEqualTo(birthDate);
    assertThat(patient.getTaxCode()).isEqualTo("RSSMRA85D12H501X");
  }

  @Test
  void shouldUpdatePatientThroughSetters() {
    Patient patient =
        new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
    LocalDate newBirthDate = LocalDate.of(1990, 6, 20);

    patient.setFirstName("Luigi");
    patient.setLastName("Bianchi");
    patient.setBirthDate(newBirthDate);
    patient.setTaxCode("BNCLGU90H20H501Z");

    assertThat(patient.getFirstName()).isEqualTo("Luigi");
    assertThat(patient.getLastName()).isEqualTo("Bianchi");
    assertThat(patient.getBirthDate()).isEqualTo(newBirthDate);
    assertThat(patient.getTaxCode()).isEqualTo("BNCLGU90H20H501Z");
  }

  @Test
  void shouldAcceptValidCreatePatientRequest() {
    CreatePatientRequest request =
        new CreatePatientRequest(
            "Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");

    Set<?> violations = validator.validate(request);

    assertThat(violations).isEmpty();
  }

  @Test
  void shouldRejectCreatePatientRequestWithInvalidData() {
    CreatePatientRequest request =
        new CreatePatientRequest("", "", LocalDate.now().plusDays(1), "");

    Set<?> violations = validator.validate(request);

    assertThat(violations).hasSize(4);
  }
}
