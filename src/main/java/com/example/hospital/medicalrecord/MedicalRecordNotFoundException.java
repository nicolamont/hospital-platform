package com.example.hospital.medicalrecord;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Eccezione sollevata quando una cartella clinica richiesta non esiste.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicalRecordNotFoundException extends RuntimeException {

    /**
     * Crea l'eccezione per una cartella clinica non trovata
     *
     * @param id identificativo della cartella clinica
     */
    public MedicalRecordNotFoundException(Long id) {
        super(("MedicalRecord with id " + id + " not found!"));
    }
}
