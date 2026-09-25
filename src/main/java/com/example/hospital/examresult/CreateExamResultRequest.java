package com.example.hospital.examresult;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** Dati richiesti per creare o aggiornare un esito di esame. */
public record CreateExamResultRequest(
    @NotNull Long medicalRecordId,
    @NotBlank String examType,
    @NotBlank String result,
    @NotNull LocalDate performedAt) {}
