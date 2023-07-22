package com.tofu.server.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Request {
    
    private String requestId;
    private LocalDate preferredDate;
    private LocalTime preferredTime;
    private String preferredGender;
    private Integer employeeId;
    private Boolean isMatched;

    public String getRequestId() {return this.requestId;}
    public void setRequestId(String requestId) {this.requestId = requestId;}

    public LocalDate getPreferredDate() {return this.preferredDate;}
    public void setPreferredDate(LocalDate preferredDate) {this.preferredDate = preferredDate;}

    public LocalTime getPreferredTime() {return this.preferredTime;}
    public void setPreferredTime(LocalTime preferredTime) {this.preferredTime = preferredTime;}

    public String getPreferredGender() {return this.preferredGender;}
    public void setPreferredGender(String preferredGender) {this.preferredGender = preferredGender;}

    public Integer getEmployeeId() {return this.employeeId;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}

    public Boolean getIsMatched() {return this.isMatched;}
    public void setIsMatched(Boolean isMatched) {this.isMatched = isMatched;}

}
