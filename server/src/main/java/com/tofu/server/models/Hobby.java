package com.tofu.server.models;

public class Hobby {
    
    private String hobbyId;
    private String hobby;
    private Integer employeeId;

    public String getHobbyId() {return this.hobbyId;}
    public void setHobbyId(String hobbyId) {this.hobbyId = hobbyId;}

    public String getHobby() {return this.hobby;}
    public void setHobby(String hobby) {this.hobby = hobby;}

    public Integer getEmployeeId() {return this.employeeId;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}

}
