package com.example.hospital.examresult;

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

/** Test della logica applicativa degli esiti degli esami. */
@ExtendWith(MockitoExtension.class)
class ExamResultServiceTest {

  @Mock private ExamResultRepository examResultRepository;
  @Mock private MedicalRecordRepository medicalRecordRepository;

  @InjectMocks private ExamResultService service;

  @Test
  void shouldCreateExamResult() {
    MedicalRecord medicalRecord = createMedicalRecord();
    CreateExamResultRequest request =
        new CreateExamResultRequest(
            1L, "Esame del sangue", "Valori nella norma", LocalDate.of(2026, 9, 25));

    when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
    when(examResultRepository.save(org.mockito.ArgumentMatchers.any(ExamResult.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    ExamResultResponse response = service.create(request);

    assertThat(response.examType()).isEqualTo("Esame del sangue");
    assertThat(response.result()).isEqualTo("Valori nella norma");
  }

  @Test
  void shouldThrowWhenMedicalRecordDoesNotExist() {
    CreateExamResultRequest request =
        new CreateExamResultRequest(99L, "Esame", "Risultato", LocalDate.of(2026, 9, 25));
    when(medicalRecordRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(MedicalRecordNotFoundException.class);
  }

  private MedicalRecord createMedicalRecord() {
    Patient patient = new Patient("Mario", "Rossi", LocalDate.of(1985, 4, 12), "RSSMRA85D12H501X");
    return new MedicalRecord(patient);
  }
}
