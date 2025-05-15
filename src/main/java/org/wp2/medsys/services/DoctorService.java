
package org.wp2.medsys.services;

import org.wp2.medsys.domain.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();
    Doctor findById(Long id);
    Doctor create(Doctor doctor);
    Doctor update(Doctor doctor);
    void deleteById(Long id);
    void deleteAll();
}
