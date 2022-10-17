package com.peluqueria.peluqueria.Service.Customer;

import com.peluqueria.peluqueria.Entity.Customer;
import com.peluqueria.peluqueria.Repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    
    private final CustomerRepository customerRepository;
    
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public Optional<Customer> findById(Long id) {
        if(!customerRepository.existsById(id))
            return Optional.empty();
        return customerRepository.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Customer save(Customer customer) {
        if(customer == null || ! StringUtils.hasLength(customer.getEmail()))
            throw new IllegalArgumentException("El cliente no es correcto");
        
        return customerRepository.save(customer);
    }
    
    @Override
    public Boolean deleteById(Long id) {
        if(!customerRepository.existsById(id))
            return false;
        
        customerRepository.deleteById(id);
        return true;
    }
}
