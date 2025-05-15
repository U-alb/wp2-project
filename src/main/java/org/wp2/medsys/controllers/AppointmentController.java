package org.wp2.medsys.controllers;

import org.springframework.web.bind.annotation.*;
import org.wp2.medsys.domain.Appointment;
import org.wp2.medsys.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService svc;

    public AppointmentController(AppointmentService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Appointment> list() {
        return svc.findAll();
    }

    @GetMapping("/{id}")
    public Appointment get(@PathVariable Long id) {
        return svc.findById(id);
    }


    @PostMapping
    public Appointment create(@RequestBody Appointment a) {
        return svc.create(a);
    }


    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment a) {
        a.setId(id);
        return svc.update(a);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.deleteById(id);
    }
}
