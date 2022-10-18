package com.peluqueria.peluqueria.Service.Employee;

import com.peluqueria.peluqueria.Entity.Employee;
import com.peluqueria.peluqueria.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
        
        employeeRepository.deleteById(id);
        return true;
    }
}
