package org.wp2.medsys.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wp2.medsys.domain.Patient;
import org.wp2.medsys.services.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService svc;

    public PatientController(PatientService svc) {
        this.svc = svc;
    }

    //get all patients
    @GetMapping
    public List<Patient> list() {
        return svc.findAll();
    }

    //patient by id
    @GetMapping("/{id}")
    public Patient get(@PathVariable Long id) {
        return svc.findById(id);
    }

    //create a new patient
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@RequestBody Patient p) {
        return svc.create(p);
    }

    //update patient
    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient p) {
        p.setId(id);
        return svc.update(p);
    }

    //delete patient
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.deleteById(id);
    }
}
