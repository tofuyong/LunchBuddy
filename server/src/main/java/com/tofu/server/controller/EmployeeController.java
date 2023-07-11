package com.tofu.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.tofu.server.models.Employee;
import com.tofu.server.service.EmployeeService;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    
    @Autowired
    EmployeeService empSvc;

    @GetMapping("/details/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
        Employee employee = empSvc.getEmployee(employeeId);
        if (employee != null) {
            return ResponseEntity.ok(employee); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> insertEmployee(@RequestBody Employee employee) {
        boolean isInserted = empSvc.insertEmployee(employee);
        if (isInserted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  
        }
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Boolean> updateEmployee(@RequestBody Employee employee, @PathVariable int employeeId) {
        boolean isUpdated = empSvc.updateEmployee(employee, employeeId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable int employeeId) {
        boolean isDeleted = empSvc.deleteEmployee(employeeId);
        if (isDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @PutMapping(path="/updatePhoto/{employeeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateEmployeePhoto(@RequestPart MultipartFile photo, @PathVariable int employeeId) {
        boolean isPhotoUpdated = empSvc.updateEmployeePhoto(photo, employeeId);
        if (isPhotoUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @DeleteMapping(path="/deletePhoto/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeePhoto(@PathVariable int employeeId) {
        boolean isPhotoDeleted = empSvc.deleteEmployeePhoto(employeeId);
        if (isPhotoDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @GetMapping("/findingLunchBuddy")
    public ResponseEntity<List<Employee>> getEmployeesFindingLunchBuddy() {
        List<Employee> employees = empSvc.getEmployeesFindingLunchBuddy();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

}
