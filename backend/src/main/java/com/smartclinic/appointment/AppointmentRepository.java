package com.smartclinic.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByDateOrderByTimeAsc(LocalDate date);
    List<Appointment> findByDoctorIdAndDateOrderByTimeAsc(Long doctorId,LocalDate date);
    List<Appointment> findByPatientEmailIgnoreCaseOrderByDateDescTimeDesc(String email);
    long countByDateAndStatusNot(LocalDate date,Appointment.Status status);
}
