package com.example.hospital.medicalrecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository per l'accesso ai dati delle cartelle cliniche dei pazienti
 */
public interface MedicalRecordRepository
    extends JpaRepository<MedicalRecord, Long> {

    Optional<MedicalRecord> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);
}
