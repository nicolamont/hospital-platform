package com.example.hospital.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/** Entità JPA che rappresenta un paziente dell'ospedale. */
@Entity
@Table(name = "patients")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @Column(name = "tax_code", nullable = false, unique = true, length = 16)
  private String taxCode;

  protected Patient() {
    // Required by JPA.
  }

  /**
   * Crea un nuovo paziente.
   *
   * @param firstName nome del paziente
   * @param lastName cognome del paziente
   * @param birthDate data di nascita del paziente
   * @param taxCode codice fiscale del paziente
   */
  public Patient(String firstName, String lastName, LocalDate birthDate, String taxCode) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.taxCode = taxCode;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }
}
