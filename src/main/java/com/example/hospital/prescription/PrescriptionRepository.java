package com.example.hospital.prescription;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository per l'accesso alle prescrizioni. */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

  List<Prescription> findByMedicalRecordId(Long medicalRecordId);
}
