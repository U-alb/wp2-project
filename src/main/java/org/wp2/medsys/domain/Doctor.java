package org.wp2.medsys.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DOCTOR")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"medicalRecords", "prescriptions", "appointments"})
public class Doctor extends User {

    /* ------------- fields that exist only for doctors ------------- */

    @Column(length = 100)
    private String spec;

    @Column(name = "license_number", length = 50, unique = true)
    private String licenseNumber;

    /* ------------- field required by the users table -------------- */

    /** shared NOT-NULL column coming from the SINGLE_TABLE strategy */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /* ------------- bi-directional relationships ------------------- */

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    /* ------------- “real” constructor ----------------------------- */

    public Doctor(String    username,
                  String    email,
                  String    passHash,
                  LocalDate dateOfBirth,
                  String    spec,
                  String    licenseNumber) {

        /* superclass fields */
        this.setUsername(username);
        this.setEmail(email);
        this.setPassHash(passHash);
        this.setRole(Role.DOCTOR);

        /* NOT-NULL column for *all* rows in the users table */
        this.dateOfBirth = dateOfBirth;

        /* doctor-specific */
        this.spec          = spec;
        this.licenseNumber = licenseNumber;
    }
}
