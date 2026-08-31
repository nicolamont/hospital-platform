package com.example.hospital.diagnosis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Dati richiesti per creare o aggiornare una diagnosi.
 *
 * @param medicalRecordId identificativo della cartella clinica
 * @param description descrizione della diagnosi
 */
public record CreateDiagnosisRequest(@NotNull Long medicalRecordId, @NotBlank String description) {}
