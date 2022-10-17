package com.peluqueria.peluqueria.Service.Customer;

import com.peluqueria.peluqueria.Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(Long id);
    
    List<Customer> findAll();
    
    Customer save(Customer customer);
    
    Boolean deleteById(Long id);
}
