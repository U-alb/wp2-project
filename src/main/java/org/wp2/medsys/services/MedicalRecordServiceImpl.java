
package org.wp2.medsys.services;

import org.springframework.stereotype.Service;
import org.wp2.medsys.domain.MedicalRecord;
import org.wp2.medsys.repositories.MedicalRecordRepository;
import org.wp2.medsys.services.MedicalRecordService;
import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository repo;
    public MedicalRecordServiceImpl(MedicalRecordRepository repo) { this.repo = repo; }
    @Override public MedicalRecord create(MedicalRecord r)   { return repo.save(r); }
    @Override public List<MedicalRecord> findAll()           { return repo.findAll(); }
    @Override public void deleteAll()                        { repo.deleteAll(); }
}
