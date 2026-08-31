package com.example.hospital.diagnosis;

import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordNotFoundException;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Gestisce la logica applicativa relativa alle diagnosi. */
@Service
public class DiagnosisService {

  private final DiagnosisRepository diagnosisRepository;
  private final MedicalRecordRepository medicalRecordRepository;

  public DiagnosisService(
      DiagnosisRepository diagnosisRepository, MedicalRecordRepository medicalRecordRepository) {
    this.diagnosisRepository = diagnosisRepository;
    this.medicalRecordRepository = medicalRecordRepository;
  }

  /**
   * Recupera tutte le diagnosi.
   *
   * @return lista delle diagnosi
   */
  @Transactional(readOnly = true)
  public List<DiagnosisResponse> findAll() {
    return diagnosisRepository.findAll().stream().map(this::toResponse).toList();
  }

  /**
   * Recupera le diagnosi di una cartella clinica.
   *
   * @param medicalRecordId identificativo della cartella clinica
   * @return lista delle diagnosi
   */
  @Transactional(readOnly = true)
  public List<DiagnosisResponse> findByMedicalRecordId(Long medicalRecordId) {
    return diagnosisRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Recupera una diagnosi tramite identificativo.
   *
   * @param id identificativo della diagnosi
   * @return diagnosi trovata
   * @throws DiagnosisNotFoundException se la diagnosi non esiste
   */
  @Transactional(readOnly = true)
  public DiagnosisResponse findById(Long id) {
    return toResponse(findEntityById(id));
  }

  /**
   * Crea una diagnosi associandola a una cartella clinica esistente.
   *
   * @param request dati della diagnosi da creare
   * @return diagnosi creata
   * @throws MedicalRecordNotFoundException se la cartella non esiste
   */
  @Transactional
  public DiagnosisResponse create(CreateDiagnosisRequest request) {
    MedicalRecord medicalRecord = findMedicalRecordById(request.medicalRecordId());
    Diagnosis diagnosis = new Diagnosis(medicalRecord, request.description());
    return toResponse(diagnosisRepository.save(diagnosis));
  }

  /**
   * Aggiorna una diagnosi esistente.
   *
   * @param id identificativo della diagnosi
   * @param request nuovi dati della diagnosi
   * @return diagnosi aggiornata
   * @throws DiagnosisNotFoundException se la diagnosi non esiste
   * @throws MedicalRecordNotFoundException se la cartella non esiste
   */
  @Transactional
  public DiagnosisResponse update(Long id, CreateDiagnosisRequest request) {
    Diagnosis diagnosis = findEntityById(id);
    MedicalRecord medicalRecord = findMedicalRecordById(request.medicalRecordId());

    diagnosis.setMedicalRecord(medicalRecord);
    diagnosis.setDescription(request.description());
    return toResponse(diagnosisRepository.save(diagnosis));
  }

  /**
   * Elimina una diagnosi.
   *
   * @param id identificativo della diagnosi
   * @throws DiagnosisNotFoundException se la diagnosi non esiste
   */
  @Transactional
  public void delete(Long id) {
    diagnosisRepository.delete(findEntityById(id));
  }

  private Diagnosis findEntityById(Long id) {
    return diagnosisRepository.findById(id).orElseThrow(() -> new DiagnosisNotFoundException(id));
  }

  private MedicalRecord findMedicalRecordById(Long id) {
    return medicalRecordRepository
        .findById(id)
        .orElseThrow(() -> new MedicalRecordNotFoundException(id));
  }

  private DiagnosisResponse toResponse(Diagnosis diagnosis) {
    return new DiagnosisResponse(
        diagnosis.getId(), diagnosis.getMedicalRecord().getId(), diagnosis.getDescription());
  }
}
