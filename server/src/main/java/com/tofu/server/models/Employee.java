package com.tofu.server.models;

public class Employee {
    
    private Integer employeeId;
    private Boolean isFinding;
    private String firstName;
    private String lastName;
    private String salutation;
    private String gender;
    private String email;
    private String department;
    private String title;
    private String photo;

    public Integer getEmployeeId() {return this.employeeId;}
    public void setEmployeeId(Integer employeeId) {this.employeeId = employeeId;}

    public Boolean getIsFinding() {return this.isFinding;}
    public void setIsFinding(Boolean isFinding) {this.isFinding = isFinding;}

    public String getFirstName() {return this.firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return this.lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getSalutation() {return this.salutation;}
    public void setSalutation(String salutation) {this.salutation = salutation;}

    public String getGender() {return this.gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getDepartment() {return this.department;}
    public void setDepartment(String department) {this.department = department;}

    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}

    public String getPhoto() {return this.photo;}
    public void setPhoto(String photo) {this.photo = photo;}

}
