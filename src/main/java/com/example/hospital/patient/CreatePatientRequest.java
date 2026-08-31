package com.example.hospital.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Dati richiesti per creare o aggiornare un paziente tramite l'API REST.
 *
 * @param firstName nome del paziente
 * @param lastName cognome del paziente
 * @param birthDate data di nascita del paziente
 * @param taxCode codice fiscale del paziente
 */
public record CreatePatientRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotNull @Past LocalDate birthDate,
    @NotBlank String taxCode) {}
