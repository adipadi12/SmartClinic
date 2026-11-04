package com.smartclinic.doctor;

import jakarta.persistence.*;
@Entity @Table(name="doctors")
public class Doctor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false) private String specialty;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String initials;
 protected Doctor() {}
 public Doctor(String name,String specialty,String email,String initials){this.name=name;this.specialty=specialty;this.email=email;this.initials=initials;}
 public Long getId(){return id;} public String getName(){return name;} public String getSpecialty(){return specialty;} public String getEmail(){return email;} public String getInitials(){return initials;}
}
