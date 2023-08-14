package com.tofu.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tofu.server.models.Request;
import com.tofu.server.repository.RequestRepository;

@Service
public class RequestService {
    
    @Autowired
    RequestRepository reqRepo;

    public List<Request> getAllUnmatchedRequests() {
        return reqRepo.getAllUnmatchedRequests();
    }

    public List<Request> getAllRequestsByEmployeeId(Integer employeeId) {
        return reqRepo.getAllRequestsByEmployeeId(employeeId);
    }

    public List<Request> getAllOpenRequestsByEmployeeId(Integer employeeId) {
        return reqRepo.getAllOpenRequestsByEmployeeId(employeeId);
    }

    public Request getRequest(String requestId) {
        return reqRepo.getRequest(requestId);
    }

    public String insertRequest(Request request) {
        Integer employeeId = request.getEmployeeId();
        // Create requestId
        String lastRequestId = reqRepo.getLastRequestIdByEmployeeId(employeeId);
        String newRequestId;
        if (lastRequestId != null) {
            int lastSerialNumber = Integer.parseInt(lastRequestId.substring(lastRequestId.lastIndexOf('R') + 1));
            int newSerialNumber = lastSerialNumber + 1;
            newRequestId = String.format("%sR%03d", employeeId, newSerialNumber);
        } else {
            newRequestId = String.format("%sR%03d", employeeId, 1);
        }
        request.setRequestId(newRequestId);
        return reqRepo.insertRequest(request);
    }
    
    public Boolean updateRequest(Request request, String requestId) {
        return reqRepo.updateRequest(request, requestId);
    }

    public Boolean updateRequestMatchStatus(Boolean isMatched, String requestId) {
        return reqRepo.updateRequestMatchStatus(isMatched, requestId);
    }

    public Boolean deleteRequest(String requestId) {
        return reqRepo.deleteRequest(requestId);
    }

}
