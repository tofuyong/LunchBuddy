package com.tofu.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tofu.server.models.Hobby;
import com.tofu.server.repository.HobbyRepository;

@Service
public class HobbyService {

    @Autowired
    HobbyRepository hobbyRepo;

    public List<Hobby> getAllHobbiesByEmployeeId(Integer employeeId) {
        return hobbyRepo.getAllHobbiesByEmployeeId(employeeId);
    }

    public Hobby getHobby(String hobbyId) {
        return hobbyRepo.getHobby(hobbyId);
    }

    public Boolean insertHobby(Hobby hobby) {
        Integer employeeId = hobby.getEmployeeId();
        // Create hobbyId
        String lastHobbyId = hobbyRepo.getLastHobbyIdByEmployeeId(employeeId);
        String newHobbyId;
        if (lastHobbyId != null) {
            int lastSerialNumber = Integer.parseInt(lastHobbyId.substring(lastHobbyId.lastIndexOf('H') + 1));
            int newSerialNumber = lastSerialNumber + 1;
            newHobbyId = String.format("%sH%03d", employeeId, newSerialNumber);
        } else {
            newHobbyId = String.format("%sH%03d", employeeId, 1);
        }
        hobby.setHobbyId(newHobbyId);
        return hobbyRepo.insertHobby(hobby);
    }
    
    public Boolean updateHobby(Hobby hobby, String hobbyId) {
        return hobbyRepo.updateHobby(hobby, hobbyId);
    }

    public Boolean deleteHobby(String hobbyId) {
        return hobbyRepo.deleteHobby(hobbyId);
    }
    
}
