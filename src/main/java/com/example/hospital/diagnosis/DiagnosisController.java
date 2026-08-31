package com.example.hospital.diagnosis;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Espone le operazioni REST per la gestione delle diagnosi. */
@RestController
@RequestMapping("/api/v1/diagnoses")
public class DiagnosisController {

  private final DiagnosisService diagnosisService;

  public DiagnosisController(DiagnosisService diagnosisService) {
    this.diagnosisService = diagnosisService;
  }

  /**
   * Restituisce tutte le diagnosi.
   *
   * @return lista delle diagnosi
   */
  @GetMapping
  public List<DiagnosisResponse> findAll() {
    return diagnosisService.findAll();
  }

  /**
   * Restituisce le diagnosi associate a una cartella clinica.
   *
   * @param medicalRecordId identificativo della cartella clinica
   * @return lista delle diagnosi
   */
  @GetMapping("/medical-record/{medicalRecordId}")
  public List<DiagnosisResponse> findByMedicalRecordId(@PathVariable Long medicalRecordId) {
    return diagnosisService.findByMedicalRecordId(medicalRecordId);
  }

  /**
   * Restituisce una diagnosi tramite identificativo.
   *
   * @param id identificativo della diagnosi
   * @return diagnosi trovata
   */
  @GetMapping("/{id}")
  public DiagnosisResponse findById(@PathVariable Long id) {
    return diagnosisService.findById(id);
  }

  /**
   * Crea una diagnosi.
   *
   * @param request dati della diagnosi da creare
   * @return diagnosi creata con risposta HTTP 201
   */
  @PostMapping
  public ResponseEntity<DiagnosisResponse> create(
      @Valid @RequestBody CreateDiagnosisRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisService.create(request));
  }

  /**
   * Aggiorna una diagnosi esistente.
   *
   * @param id identificativo della diagnosi
   * @param request nuovi dati della diagnosi
   * @return diagnosi aggiornata
   */
  @PutMapping("/{id}")
  public DiagnosisResponse update(
      @PathVariable Long id, @Valid @RequestBody CreateDiagnosisRequest request) {
    return diagnosisService.update(id, request);
  }

  /**
   * Elimina una diagnosi.
   *
   * @param id identificativo della diagnosi
   * @return risposta HTTP 204 senza contenuto
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    diagnosisService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
