package org.wp2.medsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wp2.medsys.domain.Prescription;

public interface PrescriptionRepository   extends JpaRepository<Prescription,Long> {}