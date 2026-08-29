package com.example.hospital.appointment;

import com.example.hospital.doctor.Doctor;
import com.example.hospital.patient.Patient;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entità JPA che rappresenta un appuntamento tra dottore e paziente.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date_time", nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;

    protected Appointment() {
        // Required by JPA.
    }

    /**
     * Crea un nuovo appuntamento.
     *
     * @param patient             paziente dell'appuntamento
     * @param doctor              medico dell'appuntamento
     * @param appointmentDateTime data e ora dell'appuntamento
     * @param status              stato dell'appuntamento
     */
    public Appointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime, AppointmentStatus status) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
