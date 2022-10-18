package com.peluqueria.peluqueria.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 40, name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "birth_day")
    private LocalDate birthDay;
    
    private String phone;
    
    @JsonIgnoreProperties(value = {"customer"})
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments = new ArrayList<>();
    
    
    public Customer() {
    }
    
    public Customer(Long id, String firstName, String lastName, String email, LocalDate birthDay, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.phone = phone;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getBirthDay() {
        return birthDay;
    }
    
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
