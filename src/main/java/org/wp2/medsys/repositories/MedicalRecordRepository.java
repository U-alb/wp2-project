package org.wp2.medsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wp2.medsys.domain.MedicalRecord;

public interface MedicalRecordRepository  extends JpaRepository<MedicalRecord,Long> {}