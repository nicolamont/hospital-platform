package com.example.hospital.prescription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Eccezione sollevata quando una prescrizione non esiste. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrescriptionNotFoundException extends RuntimeException {

  public PrescriptionNotFoundException(Long id) {
    super("Prescription with id " + id + " not found!");
  }
}
