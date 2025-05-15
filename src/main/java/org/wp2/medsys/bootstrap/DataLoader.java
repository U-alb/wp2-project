package org.wp2.medsys.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wp2.medsys.domain.*;
import org.wp2.medsys.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    /* ---------- services ---------- */
    private final PatientService       patientService;
    private final DoctorService        doctorService;
    private final AppointmentService   appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final PrescriptionService  prescriptionService;

    /* ---------- the encoder we’ll use everywhere ---------- */
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PatientService       patientService,
                      DoctorService        doctorService,
                      AppointmentService   appointmentService,
                      MedicalRecordService medicalRecordService,
                      PrescriptionService  prescriptionService,
                      PasswordEncoder      passwordEncoder) {   // <-- injected
        this.patientService       = patientService;
        this.doctorService        = doctorService;
        this.appointmentService   = appointmentService;
        this.medicalRecordService = medicalRecordService;
        this.prescriptionService  = prescriptionService;
        this.passwordEncoder      = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        /* -------- 1) clean slate (children → parents) -------- */
        appointmentService.deleteAll();
        medicalRecordService.deleteAll();
        prescriptionService.deleteAll();
        patientService.deleteAll();
        doctorService.deleteAll();

        /* -------- 2) patients -------- */
        Patient john = new Patient(
                "john.doe",
                "john.doe@example.com",
                passwordEncoder.encode("pass"),            // hashed ↴
                LocalDate.of(1990, 1, 1),
                "M",
                "0722-123-456",
                "123 Main St, Springfield"
        );

        Patient jane = new Patient(
                "jane.roe",
                "jane.roe@example.com",
                passwordEncoder.encode("pass"),
                LocalDate.of(1992, 6, 15),
                "F",
                "0722-654-321",
                "456 Elm St, Springfield"
        );

        john = patientService.create(john);
        jane = patientService.create(jane);

        /* -------- 3) doctors -------- */
        Doctor house = new Doctor(
                "house",
                "house@example.com",
                passwordEncoder.encode("pass"),
                LocalDate.of(1970, 5, 15),
                "Diagnostics",
                "DOC-1001"
        );

        Doctor wattson = new Doctor(
                "wattson",
                "wattson@example.com",
                passwordEncoder.encode("pass"),
                LocalDate.of(1968, 2, 13),
                "Diagnostics",
                "DOC-1002"
        );

        house   = doctorService.create(house);
        wattson = doctorService.create(wattson);

        /* -------- 4) appointments -------- */
        Appointment ap1 = new Appointment(
                LocalDateTime.of(2025, 5, 20, 9, 30),
                john,
                house,
                "Annual physical exam",
                Status.ACCEPTED
        );

        Appointment ap2 = new Appointment(
                LocalDateTime.of(2025, 5, 21, 14, 0),
                jane,
                wattson,
                "Flu-like symptoms",
                Status.PENDING
        );

        appointmentService.create(ap1);
        appointmentService.create(ap2);

        /* -------- 5) medical records -------- */
        MedicalRecord mr1 = new MedicalRecord(
                "Blood work",
                "All values within normal ranges.",
                john,
                house,
                LocalDateTime.of(2024, 12, 1, 10, 0)
        );

        medicalRecordService.create(mr1);

        /* -------- 6) prescriptions -------- */
        Prescription rx1 = new Prescription(
                LocalDate.now(),
                "Ibuprofen",
                "200 mg twice a day after meals",
                john,
                house,
                null
        );

        prescriptionService.create(rx1);

        /* -------- 7) confirm -------- */
        log.info("\n=== Sample Data Loaded ==="
                + "\nPatients:       {}", patientService.findAll());
        log.info("Doctors:        {}", doctorService.findAll());
        log.info("Appointments:   {}", appointmentService.findAll());
        log.info("MedicalRecords: {}", medicalRecordService.findAll());
        log.info("Prescriptions:  {}", prescriptionService.findAll());
    }
}
