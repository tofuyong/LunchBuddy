package com.tofu.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.tofu.server.models.Pairing;
import com.tofu.server.models.Request;
import com.tofu.server.service.PairingService;

@RestController
@RequestMapping("api/pairing")
public class PairingController {
    
    @Autowired
    PairingService pairingSvc;

    private static final Logger logger = LoggerFactory.getLogger(PairingService.class);

    @GetMapping("/all")
    public ResponseEntity<List<Pairing>> getAllPairingsByEmployeeId(@RequestParam int employeeId) {
        List<Pairing> pairings = pairingSvc.getAllPairingsByEmployeeId(employeeId);
        return ResponseEntity.ok(pairings);
    }

    @GetMapping("/allAccepted")
    public ResponseEntity<List<Pairing>> getAllAcceptedPairingsByEmployeeId(@RequestParam int employeeId) {
        List<Pairing> pairings = pairingSvc.getAllAcceptedPairingsByEmployeeId(employeeId);
        return ResponseEntity.ok(pairings);
    }

    @GetMapping("/details/{pairingId}")
    public ResponseEntity<Pairing> getPairing(@PathVariable String pairingId) {
        Pairing pairing = pairingSvc.getPairing(pairingId);
        if (pairing != null) {
            return ResponseEntity.ok(pairing); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    // @PostMapping("/add")
    // public ResponseEntity<Boolean> insertPairing(@RequestBody Pairing pairing) {
    //     boolean isInserted = pairingSvc.insertPairing(pairing);
    //     if (isInserted) {
    //         return ResponseEntity.ok(true);  
    //     } else {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  
    //     }
    // }

    @PutMapping("/updateEmployeeAccepted/{pairingId}")
    public ResponseEntity<Boolean> updatePairingEmployeeAccepted(@RequestBody Boolean employeeAccepted, @PathVariable String pairingId) {
        boolean isUpdated = pairingSvc.updatePairingEmployeeAccepted(employeeAccepted, pairingId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @PutMapping("/updatePairedEmployeeAccepted/{pairingId}")
    public ResponseEntity<Boolean> updatePairingPairedEmployeeAccepted(@RequestBody Boolean pairedEmployeeAccepted, @PathVariable String pairingId) {
        boolean isUpdated = pairingSvc.updatePairingPairedEmployeeAccepted(pairedEmployeeAccepted, pairingId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }
    
    @DeleteMapping("/delete/{pairingId}")
    public ResponseEntity<Boolean> deletePairing(@PathVariable String pairingId) {
        boolean isDeleted = pairingSvc.deletePairing(pairingId);
        if (isDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }


    /*** Match & Create Pairing ***/
    @PostMapping("/findMatch")
    public ResponseEntity<Pairing> matchAndCreatePairing(@RequestBody Request request) {
        logger.info("Controller Attempting to match and create pairing for request with ID: {}", request.getRequestId());
        Pairing newPairing = pairingSvc.matchAndCreatePairing(request);
        if (newPairing != null) {
            return ResponseEntity.ok(newPairing); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
}
