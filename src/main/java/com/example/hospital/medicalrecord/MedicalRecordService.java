package com.example.hospital.medicalrecord;

import com.example.hospital.patient.Patient;
import com.example.hospital.patient.PatientNotFoundException;
import com.example.hospital.patient.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Gestisce la logica applicativa relativa alle cartelle cliniche.
 */
@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Recupera una cartella clinica tramite il suo identificativo.
     *
     * @param id identificativo della cartella clinica
     * @return cartella clinica trovata
     * @throws MedicalRecordNotFoundException se la cartella non esiste
     */
    @Transactional(readOnly = true)
    public MedicalRecordResponse findById(Long id) {
        return toResponse(findEntityById(id));
    }

    /**
     * Recupera la cartella clinica associata a un paziente.
     *
     * @param patientId identificativo del paziente
     * @return cartella clinica trovata
     * @throws MedicalRecordNotFoundException se la cartella non esiste
     */
    @Transactional(readOnly = true)
    public MedicalRecordResponse findByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId)
            .map(this::toResponse)
            .orElseThrow(() -> new MedicalRecordNotFoundException(patientId));
    }

    /**
     * Crea una cartella clinica per un paziente.
     *
     * @param patientId identificativo del paziente
     * @return cartella clinica creata
     * @throws PatientNotFoundException            se il paziente non esiste
     * @throws MedicalRecordAlreadyExistsException se il paziente ha già una cartella
     */
    @Transactional
    public MedicalRecordResponse create(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new PatientNotFoundException(patientId));

        if (medicalRecordRepository.existsByPatientId(patientId)) {
            throw new MedicalRecordAlreadyExistsException(patientId);
        }

        MedicalRecord medicalRecord = new MedicalRecord(patient);
        return toResponse(medicalRecordRepository.save(medicalRecord));
    }

    private MedicalRecord findEntityById(Long id) {
        return medicalRecordRepository.findById(id)
            .orElseThrow(() -> new MedicalRecordNotFoundException(id));
    }

    private MedicalRecordResponse toResponse(MedicalRecord medicalRecord) {

        return new MedicalRecordResponse(
            medicalRecord.getId(),
            medicalRecord.getPatient().getId()
        );
    }
}
