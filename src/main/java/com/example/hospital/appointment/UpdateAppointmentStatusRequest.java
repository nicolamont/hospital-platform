package com.example.hospital.appointment;

import jakarta.validation.constraints.NotNull;

/**
 * Dati richiesti per aggiornare lo stato di un appuntamento.
 *
 * @param status nuovo stato dell'appuntamento
 */
public record UpdateAppointmentStatusRequest(
    @NotNull AppointmentStatus status
) {
}
