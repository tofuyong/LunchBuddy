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

import com.tofu.server.models.Preference;
import com.tofu.server.service.PreferenceService;

@RestController
@RequestMapping("api/preference")
public class PreferenceController {
    
    @Autowired
    PreferenceService prefSvc;

    @GetMapping("/all")
    public ResponseEntity<List<Preference>> getAllPreferencesByEmployeeId(@RequestParam int employeeId) {
        List<Preference> preferences = prefSvc.getAllPreferencesByEmployeeId(employeeId);
        return ResponseEntity.ok(preferences);
    }

    @GetMapping("/details/{preferenceId}")
    public ResponseEntity<Preference> getPreference(@PathVariable String preferenceId) {
        Preference preference = prefSvc.getPreference(preferenceId);
        if (preference != null) {
            return ResponseEntity.ok(preference); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> insertPreference(@RequestBody Preference preference) {
        boolean isInserted = prefSvc.insertPreference(preference);
        if (isInserted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  
        }
    }
    
    @PutMapping("/update/{preferenceId}")
    public ResponseEntity<Boolean> updatePreference(@RequestBody Preference preference, @PathVariable String preferenceId) {
        boolean isUpdated = prefSvc.updatePreference(preference, preferenceId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @DeleteMapping("/delete/{preferenceId}")
    public ResponseEntity<Boolean> deletePreference(@PathVariable String preferenceId) {
        boolean isDeleted = prefSvc.deletePreference(preferenceId);
        if (isDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }
}
