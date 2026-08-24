package com.example.hospital.doctor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestisce la logica applicativa relativa ai medici.
 */
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Recupera tutti i medici.
     *
     * @return lista dei medici
     */
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    /**
     * Recupera un medico tramite identificativo.
     *
     * @param id identificativo del medico
     * @return medico trovato
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @Transactional(readOnly = true)
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
    }

    /**
     * Salva un nuovo medico.
     *
     * @param doctor medico da salvare
     * @return medico persistito
     */
    @Transactional
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Aggiorna tutti i dati di un medico esistente.
     *
     * @param id           identificativo del medico da aggiornare
     * @param updateDoctor nuovi dati del medico
     * @return medico aggiornato
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @Transactional
    public Doctor update(Long id, Doctor updateDoctor) {
        Doctor doctor = findById(id);
        doctor.setDepartment(updateDoctor.getDepartment());
        doctor.setName(updateDoctor.getName());
        doctor.setSpecialization(updateDoctor.getSpecialization());
        return doctorRepository.save(doctor);
    }

    /**
     * Elimina un medico.
     *
     * @param id identificativo del medico da eliminare
     * @throws DoctorNotFoundException se il medico non esiste
     */
    @Transactional
    public void delete(Long id) {
        doctorRepository.delete(findById(id));
    }

}
