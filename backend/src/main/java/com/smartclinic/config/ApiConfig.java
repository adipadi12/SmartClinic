package com.smartclinic.config;

import com.smartclinic.doctor.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class ApiConfig implements WebMvcConfigurer {
 @Bean CommandLineRunner seedDoctors(DoctorRepository repo){return args->{if(repo.count()==0){repo.save(new Doctor("Dr. Maya Patel","Cardiology","maya.patel@smartclinic.dev","MP"));repo.save(new Doctor("Dr. Ethan Chen","General Medicine","ethan.chen@smartclinic.dev","EC"));repo.save(new Doctor("Dr. Sofia Reyes","Dermatology","sofia.reyes@smartclinic.dev","SR"));repo.save(new Doctor("Dr. Noah Williams","Pediatrics","noah.williams@smartclinic.dev","NW"));}};}
 @Override public void addCorsMappings(CorsRegistry registry){registry.addMapping("/api/**").allowedOrigins("http://localhost:5173").allowedMethods("GET","POST","PATCH","OPTIONS").allowedHeaders("*");}
}
