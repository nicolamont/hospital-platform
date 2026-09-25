package com.example.hospital.prescription;

/** Rappresentazione di una prescrizione restituita dall'API REST. */
public record PrescriptionResponse(
    Long id, Long medicalRecordId, String medication, String dosage, String instructions) {}
