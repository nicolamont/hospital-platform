package com.example.hospital.appointment;

import com.example.hospital.doctor.Doctor;
import com.example.hospital.doctor.DoctorNotFoundException;
import com.example.hospital.doctor.DoctorRepository;
import com.example.hospital.patient.Patient;
import com.example.hospital.patient.PatientNotFoundException;
import com.example.hospital.patient.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestisce la logica applicativa relativa agli appuntamenti.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Recupera tutti gli appuntamenti.
     *
     * @return lista di appuntamenti
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        return appointmentRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }


    /**
     * Recupera un appuntamento tramite identificativo.
     *
     * @param id identificativo dell'appuntamento
     * @return appuntamento trovato
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return toResponse(findEntityById(id));
    }

    /**
     * Crea e salva un nuovo appuntamento risolvendo i riferimenti al paziente
     * e al medico tramite i rispettivi identificativi.
     *
     * @param request dati dell'appuntamento da creare
     * @return appuntamento persistito
     * @throws PatientNotFoundException se il paziente non esiste
     * @throws DoctorNotFoundException  se il medico non esiste
     */
    @Transactional
    public AppointmentResponse create(CreateAppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
            .orElseThrow(() -> new PatientNotFoundException(request.patientId()));
        Doctor doctor = doctorRepository.findById(request.doctorId())
            .orElseThrow(() -> new DoctorNotFoundException(request.doctorId()));

        Appointment appointment = new Appointment(
            patient,
            doctor,
            request.appointmentDateTime(),
            request.status()
        );
        return toResponse(appointmentRepository.save(appointment));
    }

    /**
     * Aggiorna tutti i dati di un appuntamento esistente.
     *
     * @param id      identificativo dell'appuntamento da aggiornare
     * @param request nuovi dati dell'appuntamento
     * @return appuntamento aggiornato
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @Transactional
    public AppointmentResponse update(Long id, CreateAppointmentRequest request) {
        Appointment appointment = findEntityById(id);

        Patient patient = patientRepository.findById(request.patientId())
            .orElseThrow(() -> new PatientNotFoundException(request.patientId()));
        Doctor doctor = doctorRepository.findById(request.doctorId())
            .orElseThrow(() -> new DoctorNotFoundException(request.doctorId()));

        if (appointment.getStatus() != request.status()) {
            validateStatusTransition(appointment.getStatus(), request.status());
        }
        appointment.setStatus(request.status());
        appointment.setAppointmentDateTime(request.appointmentDateTime());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return toResponse(appointmentRepository.save(appointment));
    }

    /**
     * Elimina un appuntamento.
     *
     * @param id identificativo dell'appuntamento da eliminare
     * @throws AppointmentNotFoundException se l'appuntamento non esiste
     */
    @Transactional
    public void delete(Long id) {
        appointmentRepository.delete(findEntityById(id));
    }

    /**
     * Aggiorna lo stato di un appuntamento verificando la transizione consentita.
     *
     * @param id      identificativo dell'appuntamento
     * @param request nuovo stato dell'appuntamento
     * @return appuntamento aggiornato
     * @throws AppointmentNotFoundException                se l'appuntamento non esiste
     * @throws InvalidAppointmentStatusTransitionException se la transizione non è consentita
     */
    @Transactional
    public AppointmentResponse updateStatus(Long id, UpdateAppointmentStatusRequest request) {
        Appointment appointment = findEntityById(id);
        validateStatusTransition(appointment.getStatus(), request.status());
        appointment.setStatus(request.status());
        return toResponse(appointmentRepository.save(appointment));
    }

    private Appointment findEntityById(Long id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
            appointment.getId(),
            appointment.getPatient().getId(),
            appointment.getDoctor().getId(),
            appointment.getAppointmentDateTime(),
            appointment.getStatus()
        );
    }

    /**
     * Controlla che lo stato futuro che si vuole impostare sull'appuntamento rispetti il flow corretto
     *
     * @param currentStatus stato attuale dell'appuntamento
     * @param newStatus     stato futuro da settare
     */
    private void validateStatusTransition(
        AppointmentStatus currentStatus,
        AppointmentStatus newStatus
    ) {
        boolean validTransition = switch (currentStatus) {
            case REQUESTED -> newStatus == AppointmentStatus.CONFIRMED
                || newStatus == AppointmentStatus.CANCELLED;

            case CONFIRMED -> newStatus == AppointmentStatus.COMPLETED
                || newStatus == AppointmentStatus.CANCELLED;

            case COMPLETED, CANCELLED -> false;
        };

        if (!validTransition) {
            throw new InvalidAppointmentStatusTransitionException(
                currentStatus,
                newStatus
            );
        }
    }

}
