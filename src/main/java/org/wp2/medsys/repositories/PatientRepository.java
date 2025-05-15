package org.wp2.medsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wp2.medsys.domain.Patient;

public interface PatientRepository     extends JpaRepository<Patient,Long> {}