package com.peluqueria.peluqueria.Service.HairAssistance;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Entity.HairAssistance;
import com.peluqueria.peluqueria.Repository.HairAssistanceRepository;
import com.peluqueria.peluqueria.Service.Appointment.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service@Transactional

public class HairAssistanceServiceImpl implements HairAssistanceService{
    
    private final HairAssistanceRepository hairAssistanceRepository;
    
    private final AppointmentService appointmentService;
    
    public HairAssistanceServiceImpl(
            HairAssistanceRepository hairAssistanceRepository,
            AppointmentService appointmentService
            ) {
        this.hairAssistanceRepository = hairAssistanceRepository;
        this.appointmentService = appointmentService;
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
        
        List<Appointment> appointments = appointmentService.findAllByHairAssistanceId(id);
        
        appointments.forEach(a -> a.setHairAssistance(null));
        
        hairAssistanceRepository.deleteById(id);
        return true;
    }
}
