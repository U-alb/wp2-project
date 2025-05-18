package org.wp2.medsys.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wp2.medsys.domain.*;
import org.wp2.medsys.services.*;
import org.wp2.medsys.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/patient")
public class PatientViewController {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final PrescriptionService prescriptionService;

    public PatientViewController(
            UserRepository userRepository,
            AppointmentService appointmentService,
            MedicalRecordService medicalRecordService,
            PrescriptionService prescriptionService) {
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
        this.prescriptionService = prescriptionService;
    }

    private Patient getCurrentPatient(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        
        if (!(user instanceof Patient)) {
            throw new IllegalStateException("User is not a patient");
        }
        
        return (Patient) user;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        try {
            Patient patient = getCurrentPatient(authentication);
            model.addAttribute("username", patient.getUsername());
            model.addAttribute("appointments", patient.getAppointments());
            return "patient/patientdashboard";
        } catch (Exception e) {
            log.error("Error in showDashboard: ", e);
            model.addAttribute("error", "Failed to load dashboard: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/records")
    public String showRecords(Model model, Authentication authentication) {
        try {
            Patient patient = getCurrentPatient(authentication);
            List<MedicalRecord> records = patient.getMedicalRecords();
            
            log.info("Found {} medical records for patient {}", 
                    records != null ? records.size() : 0, 
                    patient.getUsername());
            
            model.addAttribute("username", patient.getUsername());
            model.addAttribute("records", records != null ? records : Collections.emptyList());
            return "patient/patientrecords";
        } catch (Exception e) {
            log.error("Error in showRecords: ", e);
            model.addAttribute("error", "Failed to load medical records: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/prescriptions")
    public String showPrescriptions(Model model, Authentication authentication) {
        try {
            Patient patient = getCurrentPatient(authentication);
            List<Prescription> prescriptions = patient.getPrescriptions();
            
            log.info("Found {} prescriptions for patient {}", 
                    prescriptions != null ? prescriptions.size() : 0, 
                    patient.getUsername());
            
            model.addAttribute("username", patient.getUsername());
            model.addAttribute("prescriptions", prescriptions != null ? prescriptions : Collections.emptyList());
            return "patient/patientprescriptions";
        } catch (Exception e) {
            log.error("Error in showPrescriptions: ", e);
            model.addAttribute("error", "Failed to load prescriptions: " + e.getMessage());
            return "error";
        }
    }
} 