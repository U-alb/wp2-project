package org.wp2.medsys.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.wp2.medsys.domain.Patient;
import org.wp2.medsys.repositories.PatientRepository;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repo;

    public PatientServiceImpl(PatientRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Patient> findAll() {
        return repo.findAll();
    }

    @Override
    public Patient findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    @Override
    public Patient create(Patient patient) {
        // you could zero‐out the ID to be safe:
        patient.setId(null);
        return repo.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        if (patient.getId() == null || !repo.existsById(patient.getId())) {
            throw new EntityNotFoundException("Cannot update non‐existent patient: " + patient.getId());
        }
        return repo.save(patient);
    }

    @Override
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete non‐existent patient: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }
}
