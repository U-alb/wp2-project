package org.wp2.medsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wp2.medsys.domain.Doctor;

public interface DoctorRepository      extends JpaRepository<Doctor,Long> {}