package com.example.hospital.prescription;

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

/** Entità JPA che rappresenta una prescrizione medica. */
@Entity
@Table(name = "prescriptions")
public class Prescription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "medical_record_id", nullable = false)
  private MedicalRecord medicalRecord;

  @Column(nullable = false, length = 200)
  private String medication;

  @Column(nullable = false, length = 100)
  private String dosage;

  @Column(nullable = false, length = 500)
  private String instructions;

  protected Prescription() {
    // Required by JPA.
  }

  /** Crea una prescrizione associata a una cartella clinica. */
  public Prescription(
      MedicalRecord medicalRecord, String medication, String dosage, String instructions) {
    this.medicalRecord = medicalRecord;
    this.medication = medication;
    this.dosage = dosage;
    this.instructions = instructions;
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

  public String getMedication() {
    return medication;
  }

  public void setMedication(String medication) {
    this.medication = medication;
  }

  public String getDosage() {
    return dosage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }
}
