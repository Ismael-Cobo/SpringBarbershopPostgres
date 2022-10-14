package com.peluqueria.peluqueria.Repository;

import com.peluqueria.peluqueria.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmetRepository extends JpaRepository<Appointment, Long> {
}
