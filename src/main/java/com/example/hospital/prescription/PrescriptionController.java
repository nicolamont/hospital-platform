package com.example.hospital.prescription;

import jakarta.validation.Valid;
import java.util.List;
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

/** Espone le operazioni REST per la gestione delle prescrizioni. */
@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

  private final PrescriptionService prescriptionService;

  public PrescriptionController(PrescriptionService prescriptionService) {
    this.prescriptionService = prescriptionService;
  }

  @GetMapping
  public List<PrescriptionResponse> findAll() {
    return prescriptionService.findAll();
  }

  @GetMapping("/medical-record/{medicalRecordId}")
  public List<PrescriptionResponse> findByMedicalRecordId(@PathVariable Long medicalRecordId) {
    return prescriptionService.findByMedicalRecordId(medicalRecordId);
  }

  @GetMapping("/{id}")
  public PrescriptionResponse findById(@PathVariable Long id) {
    return prescriptionService.findById(id);
  }

  @PostMapping
  public ResponseEntity<PrescriptionResponse> create(
      @Valid @RequestBody CreatePrescriptionRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionService.create(request));
  }

  @PutMapping("/{id}")
  public PrescriptionResponse update(
      @PathVariable Long id, @Valid @RequestBody CreatePrescriptionRequest request) {
    return prescriptionService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    prescriptionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
