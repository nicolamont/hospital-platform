package com.example.hospital.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Eccezione sollevata quando un appuntamento richiesto non esiste.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends RuntimeException {

    /**
     * Crea l'eccezione per un appuntamento non trovato
     *
     * @param id identificativo dell'appuntamento
     */
    public AppointmentNotFoundException(Long id) {
        super("Appointment with id " + id + " not found!");
    }
}
