package org.wp2.medsys.services;

import org.wp2.medsys.domain.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    Patient findById(Long id);
    Patient create(Patient patient);
    Patient update(Patient patient);
    void deleteById(Long id);
    void deleteAll(); }
