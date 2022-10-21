package com.peluqueria.peluqueria.Service.Customer;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Entity.Customer;
import com.peluqueria.peluqueria.Repository.AppointmetRepository;
import com.peluqueria.peluqueria.Repository.CustomerRepository;
import com.peluqueria.peluqueria.Service.Appointment.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    private final AppointmentService appointmentService;
    
    public CustomerServiceImpl(CustomerRepository customerRepository, AppointmentService appointmentService) {
        this.customerRepository = customerRepository;
        this.appointmentService = appointmentService;
    }
    
    @Override
    public Optional<Customer> findById(Long id) {
        if (! customerRepository.existsById(id)) return Optional.empty();
        return customerRepository.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Customer save(Customer customer) {
        if (customer == null || ! StringUtils.hasLength(customer.getEmail()))
            throw new IllegalArgumentException("El cliente no es correcto");
        
        // 1- Guardar usuario
        Customer customerDB = customerRepository.save(customer);
        
        // Tenemos que sacar los ids de la base de datos
        // porque sino hibernate no conoce los appointments
        
        // 2- Actualizar appointments asociados
        List<Long> appointmentsIds = customer.getAppointments().stream().map(Appointment::getId).toList();
        
        List<Appointment> appointments = appointmentService.findAllById(appointmentsIds);
        
        appointments.forEach(cita -> cita.setCustomer(customerDB));
    
        customerDB.setAppointments(appointmentService.saveAll(appointments));
        
        // 3- Desasociar appointments que no sean los que han llegado por la request:
        
        List<Appointment> appointmentDissociated = appointmentService.
                findAllByIdNotInAndCustomerId(appointmentsIds, customerDB.getId());
        appointmentDissociated.forEach(a -> a.setCustomer(null));
        
        appointmentService.saveAll(appointmentDissociated);
        
        
        return customerDB;
    }
    
    @Override
    public Boolean deleteById(Long id) {
        if (! customerRepository.existsById(id)) return false;
        
        List<Appointment> appointments = appointmentService.findAllByCustomerId(id);
        
        appointments.forEach(a -> a.setCustomer(null));
        
        customerRepository.deleteById(id);
        return true;
    }
}
