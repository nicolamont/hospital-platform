package com.example.hospital.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Richiesta SOAP per ottenere il riepilogo di una cartella clinica. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
    name = "getMedicalRecordSummaryRequest",
    namespace = "http://example.com/hospital/medical-records")
public class GetMedicalRecordSummaryRequest {

  @XmlElement(required = true)
  private Long medicalRecordId;

  public Long getMedicalRecordId() {
    return medicalRecordId;
  }

  public void setMedicalRecordId(Long medicalRecordId) {
    this.medicalRecordId = medicalRecordId;
  }
}
