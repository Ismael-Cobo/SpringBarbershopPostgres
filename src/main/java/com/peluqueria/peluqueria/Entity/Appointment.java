package com.peluqueria.peluqueria.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Appointment")
public class Appointment implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime date;
    
    private Integer duracion;
    
    @Column(length = 400)
    private String description;
    
    @JsonIgnoreProperties(value = {"appointments"})
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hair_assistance_id")
    private HairAssistance hairAssistance;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public HairAssistance getHairAssistance() {
        return hairAssistance;
    }
    
    public void setHairAssistance(HairAssistance hairAssistance) {
        this.hairAssistance = hairAssistance;
    }
    
    public Appointment() {
    }
    
    public Appointment(Long id, LocalDateTime date, Integer duracion) {
        this.id = id;
        this.date = date;
        this.duracion = duracion;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public Integer getDuracion() {
        return duracion;
    }
    
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", duracion=" + duracion +
                '}';
    }
}
