package com.smartclinic.appointment;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="appointments", uniqueConstraints=@UniqueConstraint(name="uk_doctor_slot", columnNames={"doctor_id","appointment_date","appointment_time"}))
public class Appointment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String patientName;
    @Column(nullable=false) private String patientEmail;
    @Column(nullable=false) private Long doctorId;
    @Column(nullable=false) private String doctorName;
    @Column(name="appointment_date",nullable=false) private LocalDate date;
    @Column(name="appointment_time",nullable=false) private LocalTime time;
    @Column(nullable=false) private String reason;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.BOOKED;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    protected Appointment() {}
    public Appointment(String patientName,String patientEmail,Long doctorId,String doctorName,LocalDate date,LocalTime time,String reason){this.patientName=patientName;this.patientEmail=patientEmail;this.doctorId=doctorId;this.doctorName=doctorName;this.date=date;this.time=time;this.reason=reason;}
    public enum Status { BOOKED, CHECKED_IN, COMPLETED, CANCELLED }
    public Long getId(){return id;} public String getPatientName(){return patientName;} public String getPatientEmail(){return patientEmail;} public Long getDoctorId(){return doctorId;} public String getDoctorName(){return doctorName;} public LocalDate getDate(){return date;} public LocalTime getTime(){return time;} public String getReason(){return reason;} public Status getStatus(){return status;} public LocalDateTime getCreatedAt(){return createdAt;}
    public void setStatus(Status status){this.status=status;}
}
