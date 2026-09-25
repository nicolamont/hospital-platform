package com.example.hospital.examresult;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Eccezione sollevata quando un esito di esame non esiste. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExamResultNotFoundException extends RuntimeException {

  public ExamResultNotFoundException(Long id) {
    super("Exam result with id " + id + " not found!");
  }
}
