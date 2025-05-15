package org.wp2.medsys.services;

import org.wp2.medsys.domain.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> findAll();
    Appointment findById(Long id);
    Appointment create(Appointment appointment);
    Appointment update(Appointment appointment);
    void deleteById(Long id);

    void deleteAll();
}
