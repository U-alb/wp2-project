package org.wp2.medsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wp2.medsys.domain.Appointment;

public interface AppointmentRepository    extends JpaRepository<Appointment,Long> {}