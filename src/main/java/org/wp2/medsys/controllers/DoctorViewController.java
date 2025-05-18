package org.wp2.medsys.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wp2.medsys.domain.*;
import org.wp2.medsys.services.*;
import org.wp2.medsys.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/doctor")
public class DoctorViewController {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final PrescriptionService prescriptionService;
    private final PatientService patientService;

    public DoctorViewController(
            UserRepository userRepository,
            AppointmentService appointmentService,
            MedicalRecordService medicalRecordService,
            PrescriptionService prescriptionService,
            PatientService patientService) {
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
        this.prescriptionService = prescriptionService;
        this.patientService = patientService;
    }

    private Doctor getCurrentDoctor(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        
        if (!(user instanceof Doctor)) {
            throw new IllegalStateException("User is not a doctor");
        }
        
        return (Doctor) user;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        try {
            Doctor doctor = getCurrentDoctor(authentication);
            model.addAttribute("username", doctor.getUsername());
            model.addAttribute("appointments", doctor.getAppointments());
            return "doctor/doctordashboard";
        } catch (Exception e) {
            log.error("Error in showDashboard: ", e);
            model.addAttribute("error", "Failed to load dashboard: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/records")
    public String showRecords(Model model, Authentication authentication) {
        try {
            Doctor doctor = getCurrentDoctor(authentication);
            List<MedicalRecord> records = doctor.getMedicalRecords();
            List<Patient> patients = patientService.findAll();
            
            log.info("Found {} medical records for doctor {}", 
                    records != null ? records.size() : 0, 
                    doctor.getUsername());
            
            model.addAttribute("username", doctor.getUsername());
            model.addAttribute("records", records != null ? records : Collections.emptyList());
            model.addAttribute("patients", patients);
            return "doctor/doctorrecords";
        } catch (Exception e) {
            log.error("Error in showRecords: ", e);
            model.addAttribute("error", "Failed to load medical records: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/prescriptions")
    public String showPrescriptions(Model model, Authentication authentication) {
        try {
            Doctor doctor = getCurrentDoctor(authentication);
            List<Prescription> prescriptions = doctor.getPrescriptions();
            List<Patient> patients = patientService.findAll();
            
            log.info("Found {} prescriptions for doctor {}", 
                    prescriptions != null ? prescriptions.size() : 0, 
                    doctor.getUsername());
            
            model.addAttribute("username", doctor.getUsername());
            model.addAttribute("prescriptions", prescriptions != null ? prescriptions : Collections.emptyList());
            model.addAttribute("patients", patients);
            return "doctor/doctorprescriptions";
        } catch (Exception e) {
            log.error("Error in showPrescriptions: ", e);
            model.addAttribute("error", "Failed to load prescriptions: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/records/add")
    public String addRecord(@RequestParam Long patientId,
                          @RequestParam String title,
                          @RequestParam String notes,
                          Authentication authentication) {
        try {
            Doctor doctor = getCurrentDoctor(authentication);
            Patient patient = patientService.findById(patientId);
            
            MedicalRecord record = new MedicalRecord(
                title,
                notes,
                patient,
                doctor,
                LocalDateTime.now()
            );
            
            medicalRecordService.create(record);
            return "redirect:/doctor/records?success=true";
        } catch (Exception e) {
            log.error("Error adding medical record: ", e);
            return "redirect:/doctor/records?error=" + e.getMessage();
        }
    }

    @PostMapping("/prescriptions/add")
    public String addPrescription(@RequestParam Long patientId,
                                @RequestParam String medication,
                                @RequestParam String dosage,
                                @RequestParam(required = false) String notes,
                                Authentication authentication) {
        try {
            Doctor doctor = getCurrentDoctor(authentication);
            Patient patient = patientService.findById(patientId);
            
            Prescription prescription = new Prescription(
                LocalDateTime.now().toLocalDate(),
                medication,
                dosage,
                patient,
                doctor,
                notes
            );
            
            prescriptionService.create(prescription);
            return "redirect:/doctor/prescriptions?success=true";
        } catch (Exception e) {
            log.error("Error adding prescription: ", e);
            return "redirect:/doctor/prescriptions?error=" + e.getMessage();
        }
    }
} 