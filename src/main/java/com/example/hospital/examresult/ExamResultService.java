package com.example.hospital.examresult;

import com.example.hospital.medicalrecord.MedicalRecord;
import com.example.hospital.medicalrecord.MedicalRecordNotFoundException;
import com.example.hospital.medicalrecord.MedicalRecordRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Gestisce la logica applicativa relativa agli esiti degli esami. */
@Service
public class ExamResultService {

  private final ExamResultRepository examResultRepository;
  private final MedicalRecordRepository medicalRecordRepository;

  public ExamResultService(
      ExamResultRepository examResultRepository, MedicalRecordRepository medicalRecordRepository) {
    this.examResultRepository = examResultRepository;
    this.medicalRecordRepository = medicalRecordRepository;
  }

  @Transactional(readOnly = true)
  public List<ExamResultResponse> findAll() {
    return examResultRepository.findAll().stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public List<ExamResultResponse> findByMedicalRecordId(Long medicalRecordId) {
    return examResultRepository.findByMedicalRecordId(medicalRecordId).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public ExamResultResponse findById(Long id) {
    return toResponse(findEntityById(id));
  }

  @Transactional
  public ExamResultResponse create(CreateExamResultRequest request) {
    MedicalRecord medicalRecord = findMedicalRecordById(request.medicalRecordId());
    ExamResult examResult =
        new ExamResult(medicalRecord, request.examType(), request.result(), request.performedAt());
    return toResponse(examResultRepository.save(examResult));
  }

  @Transactional
  public ExamResultResponse update(Long id, CreateExamResultRequest request) {
    ExamResult examResult = findEntityById(id);
    examResult.setMedicalRecord(findMedicalRecordById(request.medicalRecordId()));
    examResult.setExamType(request.examType());
    examResult.setResult(request.result());
    examResult.setPerformedAt(request.performedAt());
    return toResponse(examResultRepository.save(examResult));
  }

  @Transactional
  public void delete(Long id) {
    examResultRepository.delete(findEntityById(id));
  }

  private ExamResult findEntityById(Long id) {
    return examResultRepository.findById(id).orElseThrow(() -> new ExamResultNotFoundException(id));
  }

  private MedicalRecord findMedicalRecordById(Long id) {
    return medicalRecordRepository
        .findById(id)
        .orElseThrow(() -> new MedicalRecordNotFoundException(id));
  }

  private ExamResultResponse toResponse(ExamResult examResult) {
    return new ExamResultResponse(
        examResult.getId(),
        examResult.getMedicalRecord().getId(),
        examResult.getExamType(),
        examResult.getResult(),
        examResult.getPerformedAt());
  }
}
