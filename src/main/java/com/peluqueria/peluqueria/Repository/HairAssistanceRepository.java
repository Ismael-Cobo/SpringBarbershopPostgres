package com.peluqueria.peluqueria.Repository;


import com.peluqueria.peluqueria.Entity.HairAssistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HairAssistanceRepository extends JpaRepository<HairAssistance, Long> {
}
