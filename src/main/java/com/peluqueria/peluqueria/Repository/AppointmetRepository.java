package com.peluqueria.peluqueria.Repository;

import com.peluqueria.peluqueria.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmetRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findAllByCustomerEmail(String email);
    
    List<Appointment> findAllByCustomerId(Long id);
    
    List<Appointment> findAllByEmployeeDni(String dni);
    List<Appointment> findAllByDateBetween(LocalDateTime min, LocalDateTime max);
    
    List<Appointment> findAllByHairAssistancePriceLessThanEqual(Double price);
    
    List<Appointment> findAllByIdNotInAndCustomerId(List<Long> ids, Long id);
}

