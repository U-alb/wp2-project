package org.wp2.medsys.controllers;

import org.springframework.web.bind.annotation.*;
import org.wp2.medsys.domain.Doctor;
import org.wp2.medsys.services.DoctorService;

import java.util.List;

@RestController
//this is the endpoint
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService svc;

    public DoctorController(DoctorService svc) {
        this.svc = svc;
    }

   //use GET and the endpoint to get all doctors
    @GetMapping
    public List<Doctor> list() {
        return svc.findAll();
    }

    //use GET and the endpoint to get all doctors with the id
    @GetMapping("/{id}")
    public Doctor get(@PathVariable Long id) {
        return svc.findById(id);
    }

    //use POST and the endpoint to create a doctor
    @PostMapping
    public Doctor create(@RequestBody Doctor d) {
        return svc.create(d);
    }

   //use PUT and the endpoint to update doctor
    @PutMapping("/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor d) {
        d.setId(id);
        return svc.update(d);
    }

    //use DELETE + endpoint + id to delete a doctor
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.deleteById(id);
    }
}
