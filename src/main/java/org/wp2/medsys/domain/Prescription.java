package org.wp2.medsys.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"patient","doctor"})
@ToString(exclude = {"patient","doctor"})
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(length = 255, nullable = false)
    private String medication;

    @Column(length = 100, nullable = false)
    private String dosage;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Prescription(LocalDate issueDate,
                        String medication,
                        String dosage,
                        Patient patient,
                        Doctor doctor,
                        String notes) {
        this.issueDate = issueDate;
        this.medication = medication;
        this.dosage = dosage;
        this.patient = patient;
        this.doctor  = doctor;
        this.notes   = notes;
    }

}
