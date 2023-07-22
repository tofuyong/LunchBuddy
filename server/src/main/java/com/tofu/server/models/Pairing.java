package com.tofu.server.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pairing {

    private String pairingId;
    private Integer employeeId;
    private Integer pairedEmployeeId;
    private LocalDate pairingDate;
    private LocalDate lunchDate;
    private LocalTime lunchTime;
    private String lunchVenue;
    private Boolean employeeAccepted;
    private Boolean pairedEmployeeAccepted;
    
    public String getPairingId() {return this.pairingId;}
    public void setPairingId(String pairingId) {this.pairingId = pairingId;}

    public Integer getEmployeeId() {return this.employeeId;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}

    public Integer getPairedEmployeeId() {return this.pairedEmployeeId;}
    public void setPairedEmployeeId(Integer pairedEmployeeId) {this.pairedEmployeeId = pairedEmployeeId;}

    public LocalDate getPairingDate() {return this.pairingDate;}
    public void setPairingDate(LocalDate pairingDate) {this.pairingDate = pairingDate;}

    public LocalDate getLunchDate() {return this.lunchDate;}
    public void setLunchDate(LocalDate lunchDate) {this.lunchDate = lunchDate;}

    public LocalTime getLunchTime() {return this.lunchTime;}
    public void setLunchTime(LocalTime lunchTime) {this.lunchTime = lunchTime;}

    public String getLunchVenue() {return this.lunchVenue;}
    public void setLunchVenue(String lunchVenue) {this.lunchVenue = lunchVenue;}

    public Boolean getEmployeeAccepted() {return this.employeeAccepted;}
    public void setEmployeeAccepted(Boolean employeeAccepted) {this.employeeAccepted = employeeAccepted;}

    public Boolean getPairedEmployeeAccepted() {return this.pairedEmployeeAccepted;}
    public void setPairedEmployeeAccepted(Boolean pairedEmployeeAccepted) {this.pairedEmployeeAccepted = pairedEmployeeAccepted;}

}
