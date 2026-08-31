package com.example.hospital.appointment;

import java.time.LocalDateTime;

/**
 * Rappresentazione di un appuntamento restituita dall'API REST.
 *
 * @param id                  identificativo dell'appuntamento
 * @param patientId           identificativo del paziente
 * @param doctorId            identificativo del medico
 * @param appointmentDateTime data e ora dell'appuntamento
 * @param status              stato dell'appuntamento
 */
public record AppointmentResponse(
    Long id,
    Long patientId,
    Long doctorId,
    LocalDateTime appointmentDateTime,
    AppointmentStatus status
) {
}
