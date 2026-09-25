package com.example.hospital.soap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.hospital.diagnosis.Diagnosis;
import com.example.hospital.diagnosis.DiagnosisRepository;
import com.example.hospital.examresult.ExamResultRepository;
import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import com.example.hospital.patient.Patient;
import com.example.hospital.prescription.PrescriptionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test del servizio che compone il riepilogo SOAP. */
@ExtendWith(MockitoExtension.class)
class MedicalRecordSummarySoapServiceTest {

  @Mock private MedicalRecordRepository medicalRecordRepository;
  @Mock private DiagnosisRepository diagnosisRepository;
  @Mock private PrescriptionRepository prescriptionRepository;
  @Mock private ExamResultRepository examResultRepository;

  @InjectMocks private MedicalRecordSummarySoapService service;

  @Test
  void shouldBuildMedicalRecordSummary() {
    Patient patient =
        new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
    MedicalRecord medicalRecord = new MedicalRecord(patient);
    Diagnosis diagnosis = new Diagnosis(medicalRecord, "Ipertensione arteriosa lieve");

    when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
    when(diagnosisRepository.findByMedicalRecordId(1L)).thenReturn(List.of(diagnosis));
    when(prescriptionRepository.findByMedicalRecordId(1L)).thenReturn(List.of());
    when(examResultRepository.findByMedicalRecordId(1L)).thenReturn(List.of());

    GetMedicalRecordSummaryResponse response = service.findSummary(1L);

    assertThat(response.getPatient().getFirstName()).isEqualTo("Mario");
    assertThat(response.getDiagnoses()).hasSize(1);
    assertThat(response.getDiagnoses().getFirst().getDescription())
        .isEqualTo("Ipertensione arteriosa lieve");
    assertThat(response.getPrescriptions()).isEmpty();
    assertThat(response.getExamResults()).isEmpty();
  }

  @Test
  void shouldThrowSoapFaultExceptionWhenMedicalRecordDoesNotExist() {
    when(medicalRecordRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findSummary(99L))
        .isInstanceOf(MedicalRecordSoapFaultException.class)
        .hasMessage("Medical record with id 99 not found");
  }
}
