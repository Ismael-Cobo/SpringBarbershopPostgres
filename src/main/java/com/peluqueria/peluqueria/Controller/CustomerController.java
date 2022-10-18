package com.peluqueria.peluqueria.Controller;

import com.peluqueria.peluqueria.Entity.Customer;
import com.peluqueria.peluqueria.Service.Customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);
        
        return optionalCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if (customer.getId() != null) return ResponseEntity.badRequest().build();
        
        return ResponseEntity.ok(customerService.save(customer));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        if (customer.getId() == null) return ResponseEntity.badRequest().build();
        
        Optional<Customer> optionalCustomer = customerService.findById(id);
        
        if(optionalCustomer.isEmpty())
            return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok(customerService.save(customer));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean result = customerService.deleteById(id);
        
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
