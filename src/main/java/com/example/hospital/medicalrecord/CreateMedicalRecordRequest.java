package com.example.hospital.medicalrecord;

import jakarta.validation.constraints.NotNull;

/**
 * Dati richiesti per creare una cartella clinica.
 *
 * @param patientId identificativo del paziente proprietario della cartella
 */
public record CreateMedicalRecordRequest(
    @NotNull Long patientId
) {
}
