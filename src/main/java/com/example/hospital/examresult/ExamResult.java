package com.example.hospital.examresult;

import com.example.hospital.medicalrecord.MedicalRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/** Entità JPA che rappresenta l'esito di un esame. */
@Entity
@Table(name = "exam_results")
public class ExamResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "medical_record_id", nullable = false)
  private MedicalRecord medicalRecord;

  @Column(name = "exam_type", nullable = false, length = 200)
  private String examType;

  @Column(nullable = false, length = 1000)
  private String result;

  @Column(name = "performed_at", nullable = false)
  private LocalDate performedAt;

  protected ExamResult() {
    // Required by JPA.
  }

  /** Crea un esito associato a una cartella clinica. */
  public ExamResult(
      MedicalRecord medicalRecord, String examType, String result, LocalDate performedAt) {
    this.medicalRecord = medicalRecord;
    this.examType = examType;
    this.result = result;
    this.performedAt = performedAt;
  }

  public Long getId() {
    return id;
  }

  public MedicalRecord getMedicalRecord() {
    return medicalRecord;
  }

  public void setMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = medicalRecord;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public LocalDate getPerformedAt() {
    return performedAt;
  }

  public void setPerformedAt(LocalDate performedAt) {
    this.performedAt = performedAt;
  }
}
