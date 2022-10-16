package com.peluqueria.peluqueria.DTO;

public class AppointmentBenefitsDTO {
    
    private Double benefits;
    
    public AppointmentBenefitsDTO() {
    }
    
    public AppointmentBenefitsDTO(Double benefits) {
        this.benefits = benefits;
    }
    
    public Double getBenefits() {
        return benefits;
    }
    
    public void setBenefits(Double benefits) {
        this.benefits = benefits;
    }
}
