package com.example.hospital.doctor;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Espone le operazioni REST per la gestione dei medici.
 */
@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Restituisce tutti i medici registrati.
     *
     * @return lista dei medici
     */
    @GetMapping
    public List<Doctor> findAll() {
        return doctorService.findAll();
    }

    /**
     * Crea un nuovo medico.
     *
     * @param request dati del medico da creare
     * @return medico creato con risposta HTTP 201
     */
    @PostMapping
    public ResponseEntity<Doctor> create(@Valid @RequestBody CreateDoctorRequest request) {
        Doctor doctor = new Doctor(request.name(), request.specialization(), request.department());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.create(doctor));
    }

    /**
     * Restituisce un medico tramite il suo identificativo.
     *
     * @param id identificativo del medico
     * @return medico trovato
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @GetMapping("/{id}")
    public Doctor findById(@PathVariable Long id) {
        return doctorService.findById(id);
    }

    /**
     * Aggiorna completamente un medico esistente.
     *
     * @param id      identificativo del medico
     * @param request nuovi dati del medico
     * @return medico aggiornato
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @PutMapping("/{id}")
    public Doctor update(@PathVariable Long id, @Valid @RequestBody CreateDoctorRequest request) {
        Doctor doctor = new Doctor(request.name(), request.specialization(), request.department());
        return doctorService.update(id, doctor);
    }

    /**
     * Elimina un medico esistente.
     *
     * @param id identificativo del medico
     * @return risposta HTTP 204 senza contenuto
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
