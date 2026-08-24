package com.example.hospital.patient;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'accesso ai dati dei pazienti.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
