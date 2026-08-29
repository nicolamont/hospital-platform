package com.example.hospital.appointment;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Espone le operazioni REST per la gestione degli appuntamenti.
 */
@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Restituisce tutti gli appuntamenti presenti.
     *
     * @return lista degli appuntamenti
     */
    @GetMapping
    public List<Appointment> findAll() {
        return appointmentService.findAll();
    }

    /**
     * Crea un nuovo appuntamento.
     *
     * @param request dati dell'appuntamento da creare
     * @return appuntamento creato con risposta HTTP 201
     */
    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody CreateAppointmentRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(appointmentService.create(request));
    }

    /**
     * Restituisce un appuntamento tramite il suo identificativo.
     *
     * @param id identificativo dell'appuntamento
     * @return appuntamento trovato
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @GetMapping("/{id}")
    public Appointment findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }

    /**
     * Aggiorna completamente un appuntamento esistente.
     *
     * @param id      identificativo dell'appuntamento
     * @param request nuovi dati dell'appuntamento
     * @return appuntamento aggiornato
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @Valid @RequestBody CreateAppointmentRequest request) {
        return appointmentService.update(id, request);
    }

    /**
     * Elimina un appuntamento esistente.
     *
     * @param id identificativo dell'appuntamento
     * @return risposta HTTP 204 senza contenuto
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Aggiorna lo stato di un appuntamento.
     *
     * @param id identificativo dell'appuntamento
     * @param request nuovo stato dell'appuntamento
     * @return appuntamento aggiornato
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @PatchMapping("/{id}/status")
    public Appointment updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateAppointmentStatusRequest request ){
        return appointmentService.updateStatus(id, request);
    }


}
