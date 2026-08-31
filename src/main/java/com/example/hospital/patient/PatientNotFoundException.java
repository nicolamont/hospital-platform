package com.example.hospital.patient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Eccezione sollevata quando un paziente richiesto non esiste. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

  /**
   * Crea l'eccezione per un paziente non trovato.
   *
   * @param id identificativo del paziente
   */
  public PatientNotFoundException(Long id) {
    super("Patient with id " + id + " not found");
  }
}
