package com.smartclinic.appointment;

import com.smartclinic.doctor.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
public class AppointmentService {
 private final AppointmentRepository appointments; private final DoctorRepository doctors;
 public AppointmentService(AppointmentRepository appointments,DoctorRepository doctors){this.appointments=appointments;this.doctors=doctors;}
 @Transactional
 public Appointment book(CreateAppointmentRequest r){
  if(r.date().isBefore(LocalDate.now())) throw new IllegalArgumentException("Choose today or a future date.");
  if(r.date().equals(LocalDate.now()) && !r.time().isAfter(LocalTime.now())) throw new IllegalArgumentException("Choose a future time.");
  Doctor d=doctors.findById(r.doctorId()).orElseThrow(()->new NoSuchElementException("Doctor not found."));
  try{return appointments.saveAndFlush(new Appointment(r.patientName().trim(),r.patientEmail().trim().toLowerCase(),d.getId(),d.getName(),r.date(),r.time(),r.reason().trim()));}
  catch(DataIntegrityViolationException e){throw new SlotUnavailableException("That doctor already has an appointment at this time.");}
 }
 public List<Appointment> queue(LocalDate date,Long doctorId){return doctorId==null?appointments.findByDateOrderByTimeAsc(date):appointments.findByDoctorIdAndDateOrderByTimeAsc(doctorId,date);}
 @Transactional public Appointment updateStatus(Long id,Appointment.Status status){Appointment a=appointments.findById(id).orElseThrow(()->new NoSuchElementException("Appointment not found."));a.setStatus(status);return a;}
 public List<Appointment> patientAppointments(String email){return appointments.findByPatientEmailIgnoreCaseOrderByDateDescTimeDesc(email);}
 public Map<String,Object> dashboard(){LocalDate today=LocalDate.now();Map<String,Object> result=new LinkedHashMap<>();result.put("todayAppointments",appointments.countByDateAndStatusNot(today,Appointment.Status.CANCELLED));result.put("waiting",queue(today,null).stream().filter(a->a.getStatus()==Appointment.Status.BOOKED||a.getStatus()==Appointment.Status.CHECKED_IN).count());result.put("doctors",doctors.count());result.put("nextAppointment",appointments.findByDateOrderByTimeAsc(today).stream().filter(a->a.getStatus()!=Appointment.Status.CANCELLED&&a.getStatus()!=Appointment.Status.COMPLETED).findFirst().orElse(null));return result;}
 public record CreateAppointmentRequest(@jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max=100) String patientName,@jakarta.validation.constraints.Email @jakarta.validation.constraints.NotBlank String patientEmail,@jakarta.validation.constraints.NotNull Long doctorId,@jakarta.validation.constraints.NotNull LocalDate date,@jakarta.validation.constraints.NotNull LocalTime time,@jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max=500) String reason){}
 public static class SlotUnavailableException extends RuntimeException{public SlotUnavailableException(String m){super(m);}}
}
