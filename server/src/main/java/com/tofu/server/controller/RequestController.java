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

import com.tofu.server.models.Request;
import com.tofu.server.service.RequestService;

@RestController
@RequestMapping("api/request")
public class RequestController {
    
    @Autowired
    RequestService reqSvc;

    @GetMapping("/allUnmatched")
    public ResponseEntity<List<Request>> getAllUnmatchedRequests() {
        List<Request> unmatchedRequests = reqSvc.getAllUnmatchedRequests();
        return ResponseEntity.ok(unmatchedRequests);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Request>> getAllRequestsByEmployeeId(@RequestParam int employeeId) {
        List<Request> requests = reqSvc.getAllRequestsByEmployeeId(employeeId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/details/{requestId}")
    public ResponseEntity<Request> getRequest(@PathVariable String requestId) {
        Request request = reqSvc.getRequest(requestId);
        if (request != null) {
            return ResponseEntity.ok(request); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> insertRequest(@RequestBody Request request) {
        boolean isInserted = reqSvc.insertRequest(request);
        if (isInserted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);  
        }
    }
    
    @PutMapping("/update/{requestId}")
    public ResponseEntity<Boolean> updateRequest(@RequestBody Request request, @PathVariable String requestId) {
        boolean isUpdated = reqSvc.updateRequest(request, requestId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @PutMapping("/updateMatch/{requestId}")
    public ResponseEntity<Boolean> updateRequestMatchStatus(@RequestBody Boolean isMatched, @PathVariable String requestId) {
        boolean isUpdated = reqSvc.updateRequestMatchStatus(isMatched, requestId);
        if (isUpdated) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable String requestId) {
        boolean isDeleted = reqSvc.deleteRequest(requestId);
        if (isDeleted) {
            return ResponseEntity.ok(true);  
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); 
        }
    }
    
}
