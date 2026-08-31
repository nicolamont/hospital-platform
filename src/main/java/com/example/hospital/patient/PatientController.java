package com.example.hospital.patient;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Espone le operazioni REST per la gestione dei pazienti. */
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  /**
   * Restituisce tutti i pazienti registrati.
   *
   * @return lista dei pazienti
   */
  @GetMapping
  public List<Patient> findAll() {
    return patientService.findAll();
  }

  /**
   * Restituisce un paziente tramite il suo identificativo.
   *
   * @param id identificativo del paziente
   * @return paziente trovato
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @GetMapping("/{id}")
  public Patient findById(@PathVariable Long id) {
    return patientService.findById(id);
  }

  /**
   * Crea un nuovo paziente.
   *
   * @param request dati del paziente da creare
   * @return paziente creato con risposta HTTP 201
   */
  @PostMapping
  public ResponseEntity<Patient> create(@Valid @RequestBody CreatePatientRequest request) {
    Patient patient =
        new Patient(
            request.firstName(), request.lastName(), request.birthDate(), request.taxCode());

    return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(patient));
  }

  /**
   * Aggiorna completamente un paziente esistente.
   *
   * @param id identificativo del paziente
   * @param request nuovi dati del paziente
   * @return paziente aggiornato
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @PutMapping("/{id}")
  public Patient update(@PathVariable Long id, @Valid @RequestBody CreatePatientRequest request) {
    Patient patient =
        new Patient(
            request.firstName(), request.lastName(), request.birthDate(), request.taxCode());

    return patientService.update(id, patient);
  }

  /**
   * Elimina un paziente esistente.
   *
   * @param id identificativo del paziente
   * @return risposta HTTP 204 senza contenuto
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    patientService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
