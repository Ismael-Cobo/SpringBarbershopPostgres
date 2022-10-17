package com.peluqueria.peluqueria.Service.Appointment;

import com.peluqueria.peluqueria.Entity.Appointment;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * CRUD
 */

public interface AppointmentService {
    
    Optional<Appointment> findByID(Long id);
    
    List<Appointment> findAll();
    
    List<Appointment> findAllByCustomerEmail(String email) throws IllegalArgumentException;
    
    List<Appointment> findAllByEmployeeDni(String dni);
    
    List<Appointment> findAllByPriceLessThanEqual(Double price);
    
    Appointment save(Appointment appointment);
    
    boolean deleteByID(Long id);
    
    boolean deleteAll();
    
    double calculateAppointmentsBenefitsByDate(LocalDate date);
    
    double calculateAppointmentBenefitsByYearAndMonth(Year year, Month month);
    
    double calculateAppointmentBenefitsByYear(Year year);
}
