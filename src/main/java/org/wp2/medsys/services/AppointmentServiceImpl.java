package org.wp2.medsys.services;

import org.springframework.stereotype.Service;
import org.wp2.medsys.domain.Appointment;
import org.wp2.medsys.repositories.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentServiceImpl(AppointmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Appointment> findAll() {
        return repo.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such appointment: " + id));
    }

    @Override
    public Appointment create(Appointment appointment) {
        return repo.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        if (!repo.existsById(appointment.getId())) {
            throw new IllegalArgumentException("Cannot update non‐existent appointment: " + appointment.getId());
        }
        return repo.save(appointment);
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
