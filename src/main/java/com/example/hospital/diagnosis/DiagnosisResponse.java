package com.example.hospital.diagnosis;

/**
 * Rappresentazione di una diagnosi restituita dall'API REST.
 *
 * @param id identificativo della diagnosi
 * @param medicalRecordId identificativo della cartella clinica
 * @param description descrizione della diagnosi
 */
public record DiagnosisResponse(Long id, Long medicalRecordId, String description) {}
