package com.peluqueria.peluqueria.Controller;

import com.peluqueria.peluqueria.Entity.Employee;
import com.peluqueria.peluqueria.Service.Employee.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        
        return optionalEmployee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        if (employee.getId() != null) return ResponseEntity.badRequest().build();
        
        return ResponseEntity.ok(employeeService.save(employee));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        if (employee.getId() == null) return ResponseEntity.badRequest().build();
        
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        
        if(optionalEmployee.isEmpty())
            return ResponseEntity.notFound().build();
    
        return ResponseEntity.ok(employeeService.save(employee));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean result = employeeService.deleteById(id);
        
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
