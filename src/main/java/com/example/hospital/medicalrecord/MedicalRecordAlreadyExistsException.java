package com.example.hospital.medicalrecord;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Eccezione sollevata quando un paziente possiede già una cartella clinica.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class MedicalRecordAlreadyExistsException extends RuntimeException {

    /**
     * Crea l'eccezione per una cartella clinica già esistente.
     *
     * @param patientId identificativo del paziente
     */
    public MedicalRecordAlreadyExistsException(Long patientId) {
        super("Medical record for patient with id " + patientId + " already exists!");
    }
}
