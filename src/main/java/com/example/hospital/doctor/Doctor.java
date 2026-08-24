package com.example.hospital.doctor;

import jakarta.persistence.*;

/**
 * Entità JPA che rappresenta un medico dell'ospedale.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "specialization", nullable = false, length = 150)
    private String specialization;

    @Column(name = "department", nullable = false, length = 150)
    private String department;

    protected Doctor() {
        // Required by JPA.
    }

    /**
     * Crea un nuovo medico.
     *
     * @param name           nome del medico
     * @param specialization specializzazione del medico
     * @param department     reparto di appartenenza
     */
    public Doctor(String name, String specialization, String department) {
        this.name = name;
        this.specialization = specialization;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
