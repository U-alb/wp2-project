
package org.wp2.medsys.services;

import org.springframework.stereotype.Service;
import org.wp2.medsys.domain.Doctor;
import org.wp2.medsys.repositories.DoctorRepository;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repo;

    public DoctorServiceImpl(DoctorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Doctor> findAll() {
        return repo.findAll();
    }

    @Override
    public Doctor findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such doctor: " + id));
    }

    @Override
    public Doctor create(Doctor doctor) {
        // any pre‐save logic goes here
        return repo.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        // you might want to check existence first, merge fields, etc.
        if (!repo.existsById(doctor.getId())) {
            throw new IllegalArgumentException("Cannot update non‐existent doctor: " + doctor.getId());
        }
        return repo.save(doctor);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }
}
