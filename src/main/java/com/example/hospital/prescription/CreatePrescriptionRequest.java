package com.example.hospital.prescription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Dati richiesti per creare o aggiornare una prescrizione. */
public record CreatePrescriptionRequest(
    @NotNull Long medicalRecordId,
    @NotBlank String medication,
    @NotBlank String dosage,
    @NotBlank String instructions) {}
