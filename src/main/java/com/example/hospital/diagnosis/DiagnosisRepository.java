package com.example.hospital.diagnosis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository per l'accesso ai dati delle diagnosi. */
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

  List<Diagnosis> findByMedicalRecordId(Long medicalRecordId);
}
