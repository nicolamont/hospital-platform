package com.example.hospital.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/** Risposta SOAP contenente il riepilogo di una cartella clinica. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
    name = "getMedicalRecordSummaryResponse",
    namespace = "http://example.com/hospital/medical-records")
public class GetMedicalRecordSummaryResponse {

  private Long medicalRecordId;
  private PatientSummary patient;

  @XmlElementWrapper(name = "diagnoses")
  @XmlElement(name = "diagnosis")
  private List<DiagnosisSummary> diagnoses = new ArrayList<>();

  @XmlElementWrapper(name = "prescriptions")
  @XmlElement(name = "prescription")
  private List<PrescriptionSummary> prescriptions = new ArrayList<>();

  @XmlElementWrapper(name = "examResults")
  @XmlElement(name = "examResult")
  private List<ExamResultSummary> examResults = new ArrayList<>();

  public Long getMedicalRecordId() {
    return medicalRecordId;
  }

  public void setMedicalRecordId(Long medicalRecordId) {
    this.medicalRecordId = medicalRecordId;
  }

  public PatientSummary getPatient() {
    return patient;
  }

  public void setPatient(PatientSummary patient) {
    this.patient = patient;
  }

  public List<DiagnosisSummary> getDiagnoses() {
    return diagnoses;
  }

  public List<PrescriptionSummary> getPrescriptions() {
    return prescriptions;
  }

  public List<ExamResultSummary> getExamResults() {
    return examResults;
  }
}
