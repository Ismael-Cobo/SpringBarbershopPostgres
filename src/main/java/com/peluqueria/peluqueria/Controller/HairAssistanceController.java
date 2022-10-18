package com.peluqueria.peluqueria.Controller;

import com.peluqueria.peluqueria.Entity.HairAssistance;
import com.peluqueria.peluqueria.Service.HairAssistance.HairAssistanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hair")
public class HairAssistanceController {
    
    private final HairAssistanceService hairAssistanceService;
    
    public HairAssistanceController(HairAssistanceService hairAssistanceService) {
        this.hairAssistanceService = hairAssistanceService;
    }
    
    @GetMapping
    public ResponseEntity<List<HairAssistance>> findAll() {
        return ResponseEntity.ok(hairAssistanceService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HairAssistance> findById(@PathVariable Long id) {
        Optional<HairAssistance> optionalHairAssistance = hairAssistanceService.findById(id);
        
        return optionalHairAssistance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<HairAssistance> create(@RequestBody HairAssistance hairAssistance) {
        if (hairAssistance.getId() != null) return ResponseEntity.badRequest().build();
        
        return ResponseEntity.ok(hairAssistanceService.save(hairAssistance));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HairAssistance> update(@PathVariable Long id, @RequestBody HairAssistance hairAssistance) {
        if (hairAssistance.getId() == null) return ResponseEntity.badRequest().build();
        
        Optional<HairAssistance> optionalHairAssistance = hairAssistanceService.findById(id);
        
        if(optionalHairAssistance.isEmpty())
            return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok(hairAssistanceService.save(hairAssistance));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean result = hairAssistanceService.deleteById(id);
        
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
