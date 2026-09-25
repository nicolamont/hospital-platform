package com.example.hospital.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/** Endpoint SOAP per il riepilogo delle cartelle cliniche. */
@Endpoint
public class MedicalRecordEndpoint {

  private static final String NAMESPACE_URI = "http://example.com/hospital/medical-records";

  private final MedicalRecordSummarySoapService soapService;

  public MedicalRecordEndpoint(MedicalRecordSummarySoapService soapService) {
    this.soapService = soapService;
  }

  /** Gestisce la richiesta SOAP di riepilogo di una cartella clinica. */
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMedicalRecordSummaryRequest")
  @ResponsePayload
  public GetMedicalRecordSummaryResponse getMedicalRecordSummary(
      @RequestPayload GetMedicalRecordSummaryRequest request) {
    return soapService.findSummary(request.getMedicalRecordId());
  }
}
