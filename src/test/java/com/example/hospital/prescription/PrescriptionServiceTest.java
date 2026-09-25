package com.example.hospital.prescription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordNotFoundException;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import com.example.hospital.patient.Patient;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test della logica applicativa delle prescrizioni. */
@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {

  @Mock private PrescriptionRepository prescriptionRepository;
  @Mock private MedicalRecordRepository medicalRecordRepository;

  @InjectMocks private PrescriptionService service;

  @Test
  void shouldCreatePrescription() {
    MedicalRecord medicalRecord = createMedicalRecord();
    CreatePrescriptionRequest request =
        new CreatePrescriptionRequest(
            1L, "Farmaco demo", "10 mg", "Assumere una compressa al giorno");

    when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
    when(prescriptionRepository.save(org.mockito.ArgumentMatchers.any(Prescription.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    PrescriptionResponse response = service.create(request);

    assertThat(response.medication()).isEqualTo("Farmaco demo");
    assertThat(response.dosage()).isEqualTo("10 mg");
  }

  @Test
  void shouldThrowWhenMedicalRecordDoesNotExist() {
    CreatePrescriptionRequest request =
        new CreatePrescriptionRequest(99L, "Farmaco demo", "10 mg", "Istruzioni");
    when(medicalRecordRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(MedicalRecordNotFoundException.class);
  }

  private MedicalRecord createMedicalRecord() {
    Patient patient = new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
    return new MedicalRecord(patient);
  }
}
