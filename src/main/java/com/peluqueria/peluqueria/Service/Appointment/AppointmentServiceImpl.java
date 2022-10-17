package com.peluqueria.peluqueria.Service.Appointment;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Repository.AppointmetRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmetRepository appointmetRepository;
    
    public AppointmentServiceImpl(AppointmetRepository appointmetRepository) {
        this.appointmetRepository = appointmetRepository;
    }
    
    @Override
    public Optional<Appointment> findByID(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        
        return appointmetRepository.findById(id);
    }
    
    @Override
    public List<Appointment> findAll() {
        return appointmetRepository.findAll();
    }
    
    @Override
    public List<Appointment> findAllByCustomerEmail(String email) throws IllegalArgumentException {
        if (! StringUtils.hasLength(email) || ! email.contains("@"))
            throw new IllegalArgumentException("El email no es correcto");
        
        return appointmetRepository.findAllByCustomerEmail(email);
    }
    
    @Override
    public List<Appointment> findAllByEmployeeDni(String dni) {
        if (! StringUtils.hasLength(dni))
            throw new IllegalArgumentException("El dni no es correcto");
        
        return appointmetRepository.findAllByEmployeeDni(dni);
    }
    
    @Override
    public List<Appointment> findAllByPriceLessThanEqual(Double price) {
        if(price == null || price <= 0 )
            throw new IllegalArgumentException("El precio no es correcto");
        
        return appointmetRepository.findAllByHairAssistancePriceLessThanEqual(price);
    }
    
    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Cita incorrecta");
        return appointmetRepository.save(appointment);
    }
    
    @Override
    public boolean deleteByID(Long id) {
        if (id == null || appointmetRepository.existsById(id)) return false;
        
        Optional<Appointment> optionalAppointment = appointmetRepository.findById(id);
        
        if (optionalAppointment.isEmpty()) return false;
        
        appointmetRepository.delete(optionalAppointment.get());
        return true;
    }
    
    @Override
    public boolean deleteAll() {
        appointmetRepository.deleteAll();
        return true;
    }
    
    @Override
    public double calculateAppointmentsBenefitsByDate(LocalDate date) {
        if (date == null) return 0;
        
        LocalDateTime minDate = date.atTime(0, 0);
        LocalDateTime maxDate = date.atTime(23, 59);
        
        List<Appointment> appointments = appointmetRepository.findAllByDateBetween(minDate, maxDate);
        
        double total = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getHairAssistance() == null) continue;
            total += appointment.getHairAssistance().getPrice();
        }
        
        return total;
    }
    
    @Override
    public double calculateAppointmentBenefitsByYearAndMonth(Year year, Month month) {
        if (month == null || year == null) return 0;
        
        LocalDateTime minDate = LocalDateTime.of(year.getValue(), month, 1, 0, 0);
        LocalDateTime maxDate = LocalDateTime.of(LocalDate.now().getYear(), month, month.maxLength(), 23, 59);
        
        List<Appointment> appointments = appointmetRepository.findAllByDateBetween(minDate, maxDate);
        
        return extractBenefits(appointments);
    }
    
    @Override
    public double calculateAppointmentBenefitsByYear(Year year) {
        if (year == null) return 0;
        
        LocalDateTime minDate = LocalDateTime.of(year.getValue(), Month.JANUARY, 1, 0, 0);
        LocalDateTime maxDate = LocalDateTime.of(LocalDate.now().getYear(), Month.DECEMBER, Month.DECEMBER.maxLength(), 23, 59);
        
        List<Appointment> appointments = appointmetRepository.findAllByDateBetween(minDate, maxDate);
        
        return extractBenefits(appointments);
    }
    
    private static double extractBenefits(List<Appointment> appointments) {
        return appointments.stream().filter(appointment -> appointment.getHairAssistance() != null).mapToDouble(appointment -> appointment.getHairAssistance().getPrice()).sum();
    }
}
