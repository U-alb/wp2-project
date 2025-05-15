
package org.wp2.medsys.services;

import org.wp2.medsys.domain.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {
    MedicalRecord create(MedicalRecord r);
    List<MedicalRecord> findAll();
    void deleteAll();
}
