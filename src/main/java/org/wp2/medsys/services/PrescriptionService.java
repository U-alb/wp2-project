
package org.wp2.medsys.services;

import org.wp2.medsys.domain.Prescription;
import java.util.List;

public interface PrescriptionService {
    Prescription create(Prescription p);
    List<Prescription> findAll();
    void deleteAll();
}
