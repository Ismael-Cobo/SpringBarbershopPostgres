package com.peluqueria.peluqueria.Controller;

import com.peluqueria.peluqueria.DTO.AppointmentBenefitsDTO;
import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Service.Appointment.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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
        
        
        return appointmentService.findByID(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search/customer/email/{email}")
    public ResponseEntity<List<Appointment>> findAllByCustomerEmail(
            @PathVariable("customerEmail") String customerEmail
    ) {
        return ResponseEntity.ok(appointmentService.findAllByCustomerEmail(customerEmail));
    }
    
    @GetMapping("/search/customer/dni/{dni}")
    public ResponseEntity<List<Appointment>> findAllByEmployeeDni(
            @PathVariable("dni") String employeeDni
    ) {
        return ResponseEntity.ok(appointmentService.findAllByEmployeeDni(employeeDni));
    }
    
    @GetMapping("/search/hair-assistance/price/{price}")
    public ResponseEntity<List<Appointment>> findAllByPriceLessThanEqual(
            @PathVariable("price") Double price
    ) {
        return ResponseEntity.ok(appointmentService.findAllByPriceLessThanEqual(price));
    }
    
    
    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        if (appointment.getId() != null)
            return ResponseEntity.badRequest().build();
        
        return ResponseEntity.ok(appointmentService.save(appointment));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateById(@PathVariable Long id, @RequestBody Appointment appointment) {
        if (appointment.getId() == null)
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
    public ResponseEntity<AppointmentBenefitsDTO> calculateAppointmentsBenefitsByDate(
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day
    ) {
        Double benefits = appointmentService.calculateAppointmentsBenefitsByDate(LocalDate.of(year, month, day));
        
        AppointmentBenefitsDTO appointmentBenefitsDTO = new AppointmentBenefitsDTO(benefits);
        
        return ResponseEntity.ok(appointmentBenefitsDTO);
    }
    
    @GetMapping("/benefits/{year}/{month}")
    public ResponseEntity<AppointmentBenefitsDTO> calculateAppointmentBenefitsByYearAndMonth(
            @PathVariable int year,
            @PathVariable int month
    ) {
        Double benefits = appointmentService.calculateAppointmentBenefitsByYearAndMonth(Year.of(year), Month.of(month));
        
        AppointmentBenefitsDTO appointmentBenefitsDTO = new AppointmentBenefitsDTO(benefits);
        
        return ResponseEntity.ok(appointmentBenefitsDTO);
    }
    
    @GetMapping("/benefits/{year}")
    public ResponseEntity<AppointmentBenefitsDTO> calculateAppointmentBenefitsByYear(
            @PathVariable int year
    ) {
        Double benefits = appointmentService.calculateAppointmentBenefitsByYear(Year.of(year));
        
        AppointmentBenefitsDTO appointmentBenefitsDTO = new AppointmentBenefitsDTO(benefits);
        
        return ResponseEntity.ok(appointmentBenefitsDTO);
    }
    
}
