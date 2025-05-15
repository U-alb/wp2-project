// Patient.java
package org.wp2.medsys.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PATIENT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"medicalRecords", "prescriptions", "appointments"})
public class Patient extends User {

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 10)
    private String gender;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(String username,
                   String email,
                   String passHash,
                   LocalDate dateOfBirth,
                   String gender,
                   String phoneNumber,
                   String address) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassHash(passHash);
        this.setRole(Role.PATIENT);
        this.dateOfBirth = dateOfBirth;
        this.gender      = gender;
        this.phoneNumber = phoneNumber;
        this.address     = address;
    }

}
