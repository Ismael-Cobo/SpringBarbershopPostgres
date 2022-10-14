package com.peluqueria.peluqueria;

import com.peluqueria.peluqueria.Entity.Appointment;
import com.peluqueria.peluqueria.Repository.AppointmetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class PeluqueriaApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(PeluqueriaApplication.class, args);
		
	}

}
