package com.example.hospital.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/** Esito di esame restituito nella risposta SOAP. */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamResultSummary {

  private Long id;
  private String examType;
  private String result;
  private String performedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getPerformedAt() {
    return performedAt;
  }

  public void setPerformedAt(String performedAt) {
    this.performedAt = performedAt;
  }
}
