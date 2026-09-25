package com.example.hospital.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test della logica applicativa relativa ai pazienti. */
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

  @Mock private PatientRepository patientRepository;

  @InjectMocks private PatientService patientService;

  @Test
  void shouldFindAllPatients() {
    Patient patient = createPatient();
    when(patientRepository.findAll()).thenReturn(List.of(patient));

    List<Patient> result = patientService.findAll();

    assertThat(result).containsExactly(patient);
  }

  @Test
  void shouldFindPatientById() {
    Patient patient = createPatient();
    when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

    Patient result = patientService.findById(1L);

    assertThat(result).isSameAs(patient);
  }

  @Test
  void shouldThrowWhenPatientDoesNotExist() {
    when(patientRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> patientService.findById(99L))
        .isInstanceOf(PatientNotFoundException.class);
  }

  @Test
  void shouldCreatePatient() {
    Patient patient = createPatient();
    when(patientRepository.save(patient)).thenReturn(patient);

    Patient result = patientService.create(patient);

    assertThat(result).isSameAs(patient);
    verify(patientRepository).save(patient);
  }

  @Test
  void shouldUpdatePatient() {
    Patient existingPatient = createPatient();
    Patient updatedPatient =
        new Patient("Luigi", "Bianchi", LocalDate.of(1990, 6, 20), "BNCLGU90H20H501Z");
    when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
    when(patientRepository.save(any(Patient.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Patient result = patientService.update(1L, updatedPatient);

    assertThat(result.getFirstName()).isEqualTo("Luigi");
    assertThat(result.getLastName()).isEqualTo("Bianchi");
    assertThat(result.getBirthDate()).isEqualTo(LocalDate.of(1990, 6, 20));
    assertThat(result.getTaxCode()).isEqualTo("BNCLGU90H20H501Z");
  }

  @Test
  void shouldDeletePatient() {
    Patient patient = createPatient();
    when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

    patientService.delete(1L);

    verify(patientRepository).delete(patient);
  }

  private Patient createPatient() {
    return new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
  }
}
