package com.peluqueria.peluqueria.Service.HairAssistance;

import com.peluqueria.peluqueria.Entity.HairAssistance;

import java.util.List;
import java.util.Optional;

public interface HairAssistanceService {
    
    Optional<HairAssistance> findById(Long id);
    
    List<HairAssistance> findAll();
    
    HairAssistance save(HairAssistance hairAssistance);
    
    boolean deleteById(Long id);
}
