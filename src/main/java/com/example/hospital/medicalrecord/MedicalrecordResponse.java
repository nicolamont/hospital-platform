package com.example.hospital.medicalrecord;

/**
 * Rappresentazione di una cartella clinica restituita dall'API REST.
 *
 * @param id        identificativo della cartella clinica
 * @param patientId identificativo del paziente
 */
public record MedicalRecordResponse(
    Long id,
    Long patientId
) {
}
