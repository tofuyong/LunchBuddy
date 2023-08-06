package com.tofu.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tofu.server.models.Hobby;
import com.tofu.server.service.HobbyService;

@RestController
@RequestMapping("api/hobby")
public class HobbyController {

    @Autowired
    HobbyService hobbySvc;
    
    @GetMapping("/all")
    public ResponseEntity<List<Hobby>> getAllHobbiesByEmployeeId(@RequestParam int employeeId) {
        List<Hobby> hobbies = hobbySvc.getAllHobbiesByEmployeeId(employeeId);
        return ResponseEntity.ok(hobbies);
    }

    @GetMapping("/details/{hobbyId}")
    public ResponseEntity<Hobby> getHobby(@PathVariable String hobbyId) {
       Hobby hobby = hobbySvc.getHobby(hobbyId);
        if (hobby != null) {
            return ResponseEntity.ok(hobby); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> insertHobby(@RequestBody Hobby hobby) {
        boolean isInserted = hobbySvc.insertHobby(hobby);
        if (isInserted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  
        }
    }
    
    @PutMapping("/update/{hobbyId}")
    public ResponseEntity<Boolean> updateHobby(@RequestBody Hobby hobby, @PathVariable String hobbyId) {
        boolean isUpdated = hobbySvc.updateHobby(hobby, hobbyId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteHobby(@RequestParam String hobby) {
        boolean isDeleted = hobbySvc.deleteHobby(hobby);
        if (isDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

}
