package com.example.hospital.doctor;

import jakarta.validation.constraints.NotBlank;

/**
 * Dati richiesti per creare o aggiornare un medico tramite l'API REST.
 *
 * @param name nome del medico
 * @param specialization specializzazione del medico
 * @param department reparto di appartenenza
 */
public record CreateDoctorRequest(
    @NotBlank String name, @NotBlank String specialization, @NotBlank String department) {}
