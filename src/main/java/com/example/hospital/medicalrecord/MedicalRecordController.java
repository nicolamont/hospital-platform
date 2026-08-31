package com.example.hospital.medicalrecord;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Espone le operazioni REST per la gestione delle cartelle cliniche.
 */
@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Crea una cartella clinica per un paziente.
     *
     * @param request dati necessari alla creazione
     * @return cartella clinica creata con risposta HTTP 201
     */
    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(
        @Valid @RequestBody CreateMedicalRecordRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(medicalRecordService.create(request.patientId()));
    }

    /**
     * Recupera una cartella clinica tramite identificativo.
     *
     * @param id identificativo della cartella clinica
     * @return cartella clinica trovata
     */
    @GetMapping("/{id}")
    public MedicalRecordResponse findById(@PathVariable Long id) {
        return medicalRecordService.findById(id);
    }

    /**
     * Recupera la cartella clinica di un paziente.
     *
     * @param patientId identificativo del paziente
     * @return cartella clinica del paziente
     */
    @GetMapping("/patient/{patientId}")
    public MedicalRecordResponse findByPatientId(@PathVariable Long patientId) {
        return medicalRecordService.findByPatientId(patientId);
    }
}
