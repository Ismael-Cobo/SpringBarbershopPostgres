package com.peluqueria.peluqueria.Service.Employee;

import com.peluqueria.peluqueria.Entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> findById(Long id);
    
    List<Employee> findAll();
    
    Employee save(Employee employee);
    
    boolean deleteById(Long id);
    
}
