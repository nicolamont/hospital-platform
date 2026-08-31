package com.example.hospital.medicalrecord;

import com.example.hospital.patient.Patient;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entità JPA che rappresenta la cartella clinica di un paziente.
 */
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private Patient patient;

    protected MedicalRecord() {
        // Required by JPA.
    }

    /**
     * Crea una nuova cartella clinica per un paziente.
     *
     * @param patient paziente proprietario della cartella clinica
     */
    public MedicalRecord(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
