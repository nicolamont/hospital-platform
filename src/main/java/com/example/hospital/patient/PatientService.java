package com.example.hospital.patient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Gestisce la logica applicativa relativa ai pazienti. */
@Service
public class PatientService {

  private final PatientRepository patientRepository;

  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  /**
   * Recupera tutti i pazienti.
   *
   * @return lista dei pazienti
   */
  @Transactional(readOnly = true)
  public List<Patient> findAll() {
    return patientRepository.findAll();
  }

  /**
   * Recupera un paziente tramite identificativo.
   *
   * @param id identificativo del paziente
   * @return paziente trovato
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @Transactional(readOnly = true)
  public Patient findById(Long id) {
    return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
  }

  /**
   * Salva un nuovo paziente.
   *
   * @param patient paziente da salvare
   * @return paziente persistito
   */
  @Transactional
  public Patient create(Patient patient) {
    return patientRepository.save(patient);
  }

  /**
   * Aggiorna tutti i dati di un paziente esistente.
   *
   * @param id identificativo del paziente da aggiornare
   * @param updatedPatient nuovi dati del paziente
   * @return paziente aggiornato
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @Transactional
  public Patient update(Long id, Patient updatedPatient) {
    Patient patient = findById(id);
    patient.setFirstName(updatedPatient.getFirstName());
    patient.setLastName(updatedPatient.getLastName());
    patient.setBirthDate(updatedPatient.getBirthDate());
    patient.setTaxCode(updatedPatient.getTaxCode());
    return patientRepository.save(patient);
  }

  /**
   * Elimina un paziente.
   *
   * @param id identificativo del paziente da eliminare
   * @throws PatientNotFoundException se il paziente non esiste
   */
  @Transactional
  public void delete(Long id) {
    Patient patient = findById(id);
    patientRepository.delete(patient);
  }
}
