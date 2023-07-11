package com.tofu.server.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tofu.server.models.Employee;
import com.tofu.server.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    
    @Autowired
    EmployeeRepository empRepo;

    public Employee getEmployee(Integer employeeId) {
        return empRepo.getEmployee(employeeId);
    }

    public Boolean insertEmployee(Employee employee) {
        return empRepo.insertEmployee(employee);
    }

    public Boolean updateEmployee(Employee employee, Integer employeeId) {
        return empRepo.updateEmployee(employee, employeeId);
    }

    public Boolean deleteEmployee(Integer employeeId) {
        return empRepo.deleteEmployee(employeeId);
    }

    public Boolean updateEmployeePhoto(MultipartFile image, Integer employeeId) {
        if (image == null || image.isEmpty()) {
            return false;
        }
        try {
            byte[] imageBytes = image.getBytes();
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
            return empRepo.updateEmployeePhoto(imageBase64, employeeId);
        } catch (IOException e) {
            logger.error("Error occurred while trying to convert the image to Base64", e);
            return false;
        }
    }

    public Boolean deleteEmployeePhoto(Integer employeeId) {
        return empRepo.deleteEmployeePhoto(employeeId);
    }

    public List<Employee> getEmployeesFindingLunchBuddy() {
        return empRepo.getEmployeesFindingLunchBuddy();
    }

}

