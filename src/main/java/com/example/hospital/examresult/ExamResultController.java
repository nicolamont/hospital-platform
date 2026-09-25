package com.example.hospital.examresult;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Espone le operazioni REST per la gestione degli esiti degli esami. */
@RestController
@RequestMapping("/api/v1/exam-results")
public class ExamResultController {

  private final ExamResultService examResultService;

  public ExamResultController(ExamResultService examResultService) {
    this.examResultService = examResultService;
  }

  @GetMapping
  public List<ExamResultResponse> findAll() {
    return examResultService.findAll();
  }

  @GetMapping("/medical-record/{medicalRecordId}")
  public List<ExamResultResponse> findByMedicalRecordId(@PathVariable Long medicalRecordId) {
    return examResultService.findByMedicalRecordId(medicalRecordId);
  }

  @GetMapping("/{id}")
  public ExamResultResponse findById(@PathVariable Long id) {
    return examResultService.findById(id);
  }

  @PostMapping
  public ResponseEntity<ExamResultResponse> create(
      @Valid @RequestBody CreateExamResultRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(examResultService.create(request));
  }

  @PutMapping("/{id}")
  public ExamResultResponse update(
      @PathVariable Long id, @Valid @RequestBody CreateExamResultRequest request) {
    return examResultService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    examResultService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
