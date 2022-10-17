package com.peluqueria.peluqueria.Service.HairAssistance;

import com.peluqueria.peluqueria.Entity.HairAssistance;
import com.peluqueria.peluqueria.Repository.HairAssistanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class HairAssistanceServiceImpl implements HairAssistanceService{
    
    private final HairAssistanceRepository hairAssistanceRepository;
    
    public HairAssistanceServiceImpl(HairAssistanceRepository hairAssistanceRepository) {
        this.hairAssistanceRepository = hairAssistanceRepository;
    }
    
    @Override
    public Optional<HairAssistance> findById(Long id) {
        if(!hairAssistanceRepository.existsById(id))
            return Optional.empty();
        return hairAssistanceRepository.findById(id);
    }
    
    @Override
    public List<HairAssistance> findAll() {
        return hairAssistanceRepository.findAll();
    }
    
    @Override
    public HairAssistance save(HairAssistance hairAssistance) {
        if(hairAssistance == null || hairAssistance.getPrice() <= 0)
            throw new IllegalArgumentException("El servicio no es correcto");
    
        return hairAssistanceRepository.save(hairAssistance);
    }
    
    @Override
    public boolean deleteById(Long id) {
        if(!hairAssistanceRepository.existsById(id))
            return false;
    
        hairAssistanceRepository.deleteById(id);
        return true;
    }
}
