package com.example.hospital.prescription;

import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordNotFoundException;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Gestisce la logica applicativa relativa alle prescrizioni. */
@Service
public class PrescriptionService {

  private final PrescriptionRepository prescriptionRepository;
  private final MedicalRecordRepository medicalRecordRepository;

  public PrescriptionService(
      PrescriptionRepository prescriptionRepository,
      MedicalRecordRepository medicalRecordRepository) {
    this.prescriptionRepository = prescriptionRepository;
    this.medicalRecordRepository = medicalRecordRepository;
  }

  @Transactional(readOnly = true)
  public List<PrescriptionResponse> findAll() {
    return prescriptionRepository.findAll().stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public List<PrescriptionResponse> findByMedicalRecordId(Long medicalRecordId) {
    return prescriptionRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public PrescriptionResponse findById(Long id) {
    return toResponse(findEntityById(id));
  }

  @Transactional
  public PrescriptionResponse create(CreatePrescriptionRequest request) {
    MedicalRecord medicalRecord = findMedicalRecordById(request.medicalRecordId());
    Prescription prescription =
        new Prescription(
            medicalRecord, request.medication(), request.dosage(), request.instructions());
    return toResponse(prescriptionRepository.save(prescription));
  }

  @Transactional
  public PrescriptionResponse update(Long id, CreatePrescriptionRequest request) {
    Prescription prescription = findEntityById(id);
    prescription.setMedicalRecord(findMedicalRecordById(request.medicalRecordId()));
    prescription.setMedication(request.medication());
    prescription.setDosage(request.dosage());
    prescription.setInstructions(request.instructions());
    return toResponse(prescriptionRepository.save(prescription));
  }

  @Transactional
  public void delete(Long id) {
    prescriptionRepository.delete(findEntityById(id));
  }

  private Prescription findEntityById(Long id) {
    return prescriptionRepository
        .findById(id)
        .orElseThrow(() -> new PrescriptionNotFoundException(id));
  }

  private MedicalRecord findMedicalRecordById(Long id) {
    return medicalRecordRepository
        .findById(id)
        .orElseThrow(() -> new MedicalRecordNotFoundException(id));
  }

  private PrescriptionResponse toResponse(Prescription prescription) {
    return new PrescriptionResponse(
        prescription.getId(),
        prescription.getMedicalRecord().getId(),
        prescription.getMedication(),
        prescription.getDosage(),
        prescription.getInstructions());
  }
}
