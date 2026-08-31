package com.example.hospital.diagnosis;

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

/** Entità JPA che rappresenta una diagnosi nella cartella clinica. */
@Entity
@Table(name = "diagnoses")
public class Diagnosis {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "medical_record_id", nullable = false)
  private MedicalRecord medicalRecord;

  @Column(nullable = false, length = 500)
  private String description;

  protected Diagnosis() {
    // Required by JPA.
  }

  /**
   * Crea una diagnosi associata a una cartella clinica.
   *
   * @param medicalRecord cartella clinica di appartenenza
   * @param description descrizione della diagnosi
   */
  public Diagnosis(MedicalRecord medicalRecord, String description) {
    this.medicalRecord = medicalRecord;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
