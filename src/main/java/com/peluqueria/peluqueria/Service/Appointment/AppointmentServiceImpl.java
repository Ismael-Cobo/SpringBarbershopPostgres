package com.peluqueria.peluqueria.Service.Appointment;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Repository.AppointmetRepository;
import com.peluqueria.peluqueria.Repository.CustomerRepository;
import com.peluqueria.peluqueria.Repository.EmployeeRepository;
import com.peluqueria.peluqueria.Repository.HairAssistanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmetRepository appointmetRepository;
    private final CustomerRepository customerRepository;
    private final HairAssistanceRepository hairAssistanceRepository;
    private final EmployeeRepository employeeRepository;
    
    public AppointmentServiceImpl(
            AppointmetRepository appointmetRepository,
            CustomerRepository customerRepository,
            HairAssistanceRepository hairAssistanceRepository,
            EmployeeRepository employeeRepository
            ) {
        this.appointmetRepository = appointmetRepository;
        this.customerRepository = customerRepository;
        this.hairAssistanceRepository = hairAssistanceRepository;
        this.employeeRepository = employeeRepository;
    }
    
    @Override
    public Optional<Appointment> findByID(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        
        return appointmetRepository.findById(id);
    }
    
    @Override
    public List<Appointment> findAllById(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids))
            return new ArrayList<>();
        return appointmetRepository.findAllById(ids);
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
    public List<Appointment> findAllByCustomerId(Long id) {
        if(id == null)
            throw new IllegalArgumentException("El cliente no existe");
        
        return appointmetRepository.findAllByCustomerId(id);
    }
    
    @Override
    public List<Appointment> findAllByHairAssistanceId(Long id) {
        if(id == null)
            throw new IllegalArgumentException("El cliente no existe");
        
        return appointmetRepository.findAllByHairAssistanceId(id);
    }
    
    @Override
    public List<Appointment> findAllByPriceLessThanEqual(Double price) {
        if(price == null || price <= 0 )
            throw new IllegalArgumentException("El precio no es correcto");
        
        return appointmetRepository.findAllByHairAssistancePriceLessThanEqual(price);
    }
    
    @Override
    public List<Appointment> findAllByIdNotInAndCustomerId(List<Long> ids, Long id) {
        if(id == null || id <= 0)
            return new ArrayList<>();
        
        if(ids == null)
            ids = new ArrayList<>();
        
        return appointmetRepository.findAllByIdNotInAndCustomerId(ids, id);
    }
    
    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null || appointment.getDate() == null)
            throw new IllegalArgumentException("Cita incorrecta");
        
        if(!customerRepository.existsById(appointment.getCustomer().getId()))
            throw new IllegalArgumentException("El cliente no existe");
    
        if(!hairAssistanceRepository.existsById(appointment.getHairAssistance().getId()))
            throw new IllegalArgumentException("El tipo de servicio no existe");
    
        if(!employeeRepository.existsById(appointment.getEmployee().getId()))
            throw new IllegalArgumentException("El empleado no existe");
        
        Appointment appointment1 = appointmetRepository.save(appointment);
        
        appointment1.setCustomer(customerRepository.findById(appointment1.getCustomer().getId()).get());
        appointment1.setHairAssistance(hairAssistanceRepository.findById(appointment1.getHairAssistance().getId()).get());
        appointment1.setEmployee(employeeRepository.findById(appointment1.getEmployee().getId()).get());
        return appointment;
    }
    
    @Override
    public List<Appointment> saveAll(List<Appointment> appointments) {
        if(CollectionUtils.isEmpty(appointments))
            return new ArrayList<>();
        return appointmetRepository.saveAll(appointments);
    }
    
    @Override
    public Appointment saveAndReturn(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Cita incorrecta");
        Appointment appointmentSaved = appointmetRepository.save(appointment);
    
        Optional<Appointment> appointment1 = this.findByID(appointmentSaved.getId());
        if (appointment1.isEmpty()) return appointmentSaved;
        
        return appointment1.get();
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
