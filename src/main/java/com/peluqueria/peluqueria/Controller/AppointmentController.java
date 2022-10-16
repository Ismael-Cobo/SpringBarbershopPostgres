package com.peluqueria.peluqueria.Controller;

import com.peluqueria.peluqueria.DTO.AppointmentBenefitsDTO;
import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Service.Appointment.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    
    private final AppointmentService appointmentService;
    
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    @GetMapping
    public List<Appointment> findAll() {
        return appointmentService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
//        Optional<Appointment> optionalAppointment = appointmentService.findByID(id);
//
//        if(optionalAppointment.isEmpty()) return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok(optionalAppointment.get());
        
        return appointmentService.findByID(id)
                .map(appointment -> ResponseEntity.ok(appointment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        if(appointment.getId() != null)
            return ResponseEntity.badRequest().build();
        
        return ResponseEntity.ok(appointmentService.save(appointment));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateById(@PathVariable Long id, @RequestBody Appointment appointment) {
        if(appointment.getId() == null)
            return ResponseEntity.badRequest().build();
        
        Optional<Appointment> optionalAppointment = appointmentService.findByID(id);
        
        if (optionalAppointment.isEmpty()) return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok(appointmentService.save(appointment));
        
//        return appointmentService.findByID(id)
//                .map(appointment1 -> ResponseEntity.ok(appointmentService.save(optionalAppointment.get())))
//                .orElseGet(() ->ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean result = appointmentService.deleteByID(id);
        
        if (result) return ResponseEntity.noContent().build();
        
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/benefits/{year}/{month}/{day}")
    public ResponseEntity<AppointmentBenefitsDTO> benefitsYearMonthDay(
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day
    ) {
        Double benefits = appointmentService.calculateAppointmentsBenefitsByDate(LocalDate.of(year, month, day));
        
        AppointmentBenefitsDTO appointmentBenefitsDTO = new AppointmentBenefitsDTO(benefits);
        
        return ResponseEntity.ok(appointmentBenefitsDTO);
    }
    
}
