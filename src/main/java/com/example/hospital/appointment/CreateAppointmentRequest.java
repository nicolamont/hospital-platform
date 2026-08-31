package com.example.hospital.appointment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Dati richiesti per creare o aggiornare un appuntamento.
 *
 * @param patientId riferimento al paziente dell'appuntamento
 * @param doctorId riferimento al dottore dell'appuntamento
 * @param appointmentDateTime data dell'appuntamento
 * @param status stato dell'appuntamento
 */
public record CreateAppointmentRequest(
    @NotNull Long patientId,
    @NotNull Long doctorId,
    @NotNull LocalDateTime appointmentDateTime,
    @NotNull AppointmentStatus status) {}
