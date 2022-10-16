package com.peluqueria.peluqueria.Service.Appointment;

import com.peluqueria.peluqueria.Entity.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * CRUD
 */

public interface AppointmentService {
    
    Optional<Appointment> findByID(Long id);
    
    List<Appointment> findAll();
    
    List<Appointment> findAllByCustomerEmail(String email) throws IllegalArgumentException;
    
    Appointment save(Appointment appointment);
    
    boolean deleteByID(Long id);
    
    boolean deleteAll();
    
    double calculateAppointmentsBenefitsByDate(LocalDate date);
    
    
}
