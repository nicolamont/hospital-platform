package com.example.hospital.examresult;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository per l'accesso agli esiti degli esami. */
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

  List<ExamResult> findByMedicalRecordId(Long medicalRecordId);
}
