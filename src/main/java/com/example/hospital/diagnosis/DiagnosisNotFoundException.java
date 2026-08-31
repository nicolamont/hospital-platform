package com.example.hospital.diagnosis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Eccezione sollevata quando una diagnosi richiesta non esiste. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DiagnosisNotFoundException extends RuntimeException {

  /**
   * Crea l'eccezione per una diagnosi non trovata.
   *
   * @param id identificativo della diagnosi
   */
  public DiagnosisNotFoundException(Long id) {
    super("Diagnosis with id " + id + " not found!");
  }
}
