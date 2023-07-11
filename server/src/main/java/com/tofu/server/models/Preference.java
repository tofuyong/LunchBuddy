package com.tofu.server.models;

import java.sql.Date;
import java.sql.Time;

public class Preference {
    
    private String preferenceId;
    private Date preferredDate;
    private Time preferredTime;
    private String preferredGender;
    private Integer employeeId;

    public String getPreferenceId() {return this.preferenceId;}
    public void setPreferenceId(String preferenceId) {this.preferenceId = preferenceId;}

    public Date getPreferredDate() {return this.preferredDate;}
    public void setPreferredDate(Date preferredDate) {this.preferredDate = preferredDate;}

    public Time getPreferredTime() {return this.preferredTime;}
    public void setPreferredTime(Time preferredTime) {this.preferredTime = preferredTime;}

    public String getPreferredGender() {return this.preferredGender;}
    public void setPreferredGender(String preferredGender) {this.preferredGender = preferredGender;}
    
    public Integer getEmployeeId() {return this.employeeId;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}

}
