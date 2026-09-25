package com.example.hospital.soap;

import com.example.hospital.diagnosis.Diagnosis;
import com.example.hospital.diagnosis.DiagnosisRepository;
import com.example.hospital.examresult.ExamResult;
import com.example.hospital.examresult.ExamResultRepository;
import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import com.example.hospital.prescription.Prescription;
import com.example.hospital.prescription.PrescriptionRepository;
import com.example.hospital.patient.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Costruisce il riepilogo SOAP leggendo le informazioni della cartella clinica. */
@Service
public class MedicalRecordSummarySoapService {

  private final MedicalRecordRepository medicalRecordRepository;
  private final DiagnosisRepository diagnosisRepository;
  private final PrescriptionRepository prescriptionRepository;
  private final ExamResultRepository examResultRepository;

  public MedicalRecordSummarySoapService(
      MedicalRecordRepository medicalRecordRepository,
      DiagnosisRepository diagnosisRepository,
      PrescriptionRepository prescriptionRepository,
      ExamResultRepository examResultRepository) {
    this.medicalRecordRepository = medicalRecordRepository;
    this.diagnosisRepository = diagnosisRepository;
    this.prescriptionRepository = prescriptionRepository;
    this.examResultRepository = examResultRepository;
  }

  /** Recupera e compone il riepilogo richiesto dal client SOAP. */
  @Transactional(readOnly = true)
  public GetMedicalRecordSummaryResponse findSummary(Long medicalRecordId) {
    MedicalRecord medicalRecord =
        medicalRecordRepository
            .findById(medicalRecordId)
            .orElseThrow(() -> new MedicalRecordSoapFaultException(medicalRecordId));

    GetMedicalRecordSummaryResponse response = new GetMedicalRecordSummaryResponse();
    response.setMedicalRecordId(medicalRecord.getId());
    response.setPatient(toPatientSummary(medicalRecord.getPatient()));

    diagnosisRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toDiagnosisSummary)
        .forEach(response.getDiagnoses()::add);
    prescriptionRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toPrescriptionSummary)
        .forEach(response.getPrescriptions()::add);
    examResultRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toExamResultSummary)
        .forEach(response.getExamResults()::add);

    return response;
  }

  private PatientSummary toPatientSummary(Patient patient) {
    PatientSummary summary = new PatientSummary();
    summary.setId(patient.getId());
    summary.setFirstName(patient.getFirstName());
    summary.setLastName(patient.getLastName());
    summary.setBirthDate(patient.getBirthDate().toString());
    summary.setTaxCode(patient.getTaxCode());
    return summary;
  }

  private DiagnosisSummary toDiagnosisSummary(Diagnosis diagnosis) {
    DiagnosisSummary summary = new DiagnosisSummary();
    summary.setId(diagnosis.getId());
    summary.setDescription(diagnosis.getDescription());
    return summary;
  }

  private PrescriptionSummary toPrescriptionSummary(Prescription prescription) {
    PrescriptionSummary summary = new PrescriptionSummary();
    summary.setId(prescription.getId());
    summary.setMedication(prescription.getMedication());
    summary.setDosage(prescription.getDosage());
    summary.setInstructions(prescription.getInstructions());
    return summary;
  }

  private ExamResultSummary toExamResultSummary(ExamResult examResult) {
    ExamResultSummary summary = new ExamResultSummary();
    summary.setId(examResult.getId());
    summary.setExamType(examResult.getExamType());
    summary.setResult(examResult.getResult());
    summary.setPerformedAt(examResult.getPerformedAt().toString());
    return summary;
  }
}
