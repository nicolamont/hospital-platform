package com.example.hospital.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository per l'accesso ai dati dei medici. */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
