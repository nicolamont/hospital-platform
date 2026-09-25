package com.example.hospital.soap;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/** SOAP Fault restituito quando la cartella clinica richiesta non esiste. */
@SoapFault(
    faultCode = FaultCode.CLIENT,
    faultStringOrReason = "The requested medical record does not exist")
public class MedicalRecordSoapFaultException extends RuntimeException {

  public MedicalRecordSoapFaultException(Long medicalRecordId) {
    super("Medical record with id " + medicalRecordId + " not found");
  }
}
