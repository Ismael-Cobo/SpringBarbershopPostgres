package com.peluqueria.peluqueria.Service.Employee;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Entity.Employee;
import com.peluqueria.peluqueria.Repository.EmployeeRepository;
import com.peluqueria.peluqueria.Service.Appointment.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    private final AppointmentService appointmentService;
    
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            AppointmentService appointmentService
            ) {
        this.employeeRepository = employeeRepository;
        this.appointmentService = appointmentService;
    }
    
    @Override
    public Optional<Employee> findById(Long id) {
        
        return (id == null || id <= 0)
            ? Optional.empty()
            : employeeRepository.findById(id);
    }
    
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    @Override
    public Employee save(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Empleado incorrecto");
        return employeeRepository.save(employee);
    }
    
    @Override
    public boolean deleteById(Long id) {
        if(id == null) return false;
        
        List<Appointment> appointments = appointmentService.findAllByHairAssistanceId(id);
        
        appointments.forEach(a -> a.setHairAssistance(null));
        
        employeeRepository.deleteById(id);
        return true;
    }
}
