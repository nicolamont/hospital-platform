package com.example.hospital.examresult;

import java.time.LocalDate;

/** Rappresentazione di un esito di esame restituita dall'API REST. */
public record ExamResultResponse(
    Long id, Long medicalRecordId, String examType, String result, LocalDate performedAt) {}
