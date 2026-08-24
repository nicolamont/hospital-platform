package com.example.hospital.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Eccezione sollevata quando un medico richiesto non esiste.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoctorNotFoundException extends RuntimeException {

    /**
     * Crea l'eccezione per un medico non trovato.
     *
     * @param id identificativo del medico
     */
    public DoctorNotFoundException(Long id) {
        super("Doctor with id " + id + " not found!");
    }
}
