package com.example.hospital.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'accesso ai dati degli appuntamenti
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
