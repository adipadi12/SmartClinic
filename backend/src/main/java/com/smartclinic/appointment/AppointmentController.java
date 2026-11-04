package com.smartclinic.appointment;

import com.smartclinic.doctor.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController @RequestMapping("/api")
public class AppointmentController {
 private final AppointmentService service; private final AppointmentRepository appointments; private final DoctorRepository doctors;
 public AppointmentController(AppointmentService service,AppointmentRepository appointments,DoctorRepository doctors){this.service=service;this.appointments=appointments;this.doctors=doctors;}
 @GetMapping("/doctors") public List<Doctor> doctors(){return doctors.findAll();}
 @GetMapping("/appointments") public List<Appointment> appointments(@RequestParam(required=false) LocalDate date,@RequestParam(required=false) Long doctorId){return date==null?appointments.findAll():service.queue(date,doctorId);}
 @GetMapping("/appointments/queue") public List<Appointment> queue(@RequestParam(defaultValue="") String date,@RequestParam(required=false) Long doctorId){return service.queue(date.isBlank()?LocalDate.now():LocalDate.parse(date),doctorId);}
 @PostMapping("/appointments") @ResponseStatus(HttpStatus.CREATED) public Appointment book(@Valid @RequestBody AppointmentService.CreateAppointmentRequest request){return service.book(request);}
 @PatchMapping("/appointments/{id}/status") public Appointment status(@PathVariable Long id,@RequestBody StatusRequest request){return service.updateStatus(id,request.status());}
 @GetMapping("/patients/{email}/appointments") public List<Appointment> patientAppointments(@PathVariable String email){return service.patientAppointments(email);}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){return service.dashboard();}
 public record StatusRequest(Appointment.Status status){}
}
