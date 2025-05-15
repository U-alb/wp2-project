
package org.wp2.medsys.services;

import org.springframework.stereotype.Service;
import org.wp2.medsys.domain.Prescription;
import org.wp2.medsys.repositories.PrescriptionRepository;
import org.wp2.medsys.services.PrescriptionService;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository repo;
    public PrescriptionServiceImpl(PrescriptionRepository repo) { this.repo = repo; }
    @Override public Prescription create(Prescription p)      { return repo.save(p); }
    @Override public List<Prescription> findAll()             { return repo.findAll(); }
    @Override public void deleteAll()                         { repo.deleteAll(); }
}
