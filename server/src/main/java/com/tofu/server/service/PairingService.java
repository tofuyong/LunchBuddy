package com.tofu.server.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tofu.server.models.Pairing;
import com.tofu.server.models.Request;
import com.tofu.server.repository.PairingRepository;
import com.tofu.server.repository.RequestRepository;

import jakarta.transaction.Transactional;

@Service
public class PairingService {
    
    @Autowired
    PairingRepository pairingRepo;

    @Autowired
    RequestRepository reqRepo;

    private static final Logger logger = LoggerFactory.getLogger(PairingService.class);

    public List<Pairing> getAllPairingsByEmployeeId(Integer employeeId) {
        return pairingRepo.getAllPairingsByEmployeeId(employeeId);
    }

    public List<Pairing> getAllAcceptedPairingsByEmployeeId(Integer employeeId) {
        return pairingRepo.getAllAcceptedPairingsByEmployeeId(employeeId);
    }

    public Pairing getPairing(String pairingId) {
        return pairingRepo.getPairing(pairingId);
    }

    // public Boolean insertPairing(Pairing pairing) {
    //     // Create pairingId
    //     String lastPairingId = pairingRepo.getLastPairingId();
    //     String newPairingId;
    //     if (lastPairingId != null) {
    //         int lastSerialNumber = Integer.parseInt(lastPairingId.substring(lastPairingId.lastIndexOf('P') + 1));
    //         int newSerialNumber = lastSerialNumber + 1;
    //         newPairingId = String.format("P%04d", newSerialNumber);
    //     } else {
    //         newPairingId = String.format("P%04d", 1);
    //     }
    //     pairing.setPairingId(newPairingId);
    //     return pairingRepo.insertPairing(pairing);
    // }
    
    public Boolean updatePairingEmployeeAccepted(Boolean employeeAccepted, String pairingId) {
        return pairingRepo.updatePairingEmployeeAccepted(employeeAccepted, pairingId);
    }

    public Boolean updatePairingPairedEmployeeAccepted(Boolean pairedEmployeeAccepted, String pairingId) {
        return pairingRepo.updatePairingPairedEmployeeAccepted(pairedEmployeeAccepted, pairingId);
    }

    public Boolean deletePairing(String pairingId) {
        return pairingRepo.deletePairing(pairingId);
    }

    
    /*** Match & Create Pairing ***/
    // @Transactional
    // public Pairing matchAndCreatePairing(Request request) {
    //     logger.info("Attempting to match and create pairing for request with ID: {}", request.getRequestId());
    //     // Step 1: Get all unmatched requests
    //     List<Request> allUnmatchedRequests = reqRepo.getAllUnmatchedRequests();
    //     logger.info("Unmatched requests: {}", allUnmatchedRequests);

    //     // Step 2: Find potential matches from all unmatched requests
    //     List<Request> potentialMatches = new ArrayList<>();
    //     for (Request potentialMatch : allUnmatchedRequests) {
    //         if (!potentialMatch.getEmployeeId().equals(request.getEmployeeId())) {
    //             if (potentialMatch.getPreferredDate().equals(request.getPreferredDate()) &&
    //                 potentialMatch.getPreferredTime().equals(request.getPreferredTime())) {
    //                 if (request.getPreferredGender().equals("No Preference") ||
    //                     potentialMatch.getPreferredGender().equals(request.getPreferredGender())) {
    //                     if(!pairingRepo.hasPairedBeforeInLastSixMonths(request.getEmployeeId(), potentialMatch.getEmployeeId())) {
    //                         potentialMatches.add(potentialMatch);
    //                     }
    //                 }
    //             }
    //         }
    //     }

    //     // If no matches found, request remains open, no Pairing created
    //     if (potentialMatches.isEmpty()) {
    //         return null;
    //     }

    //     // Step 3: If a match or multiple matches found, choose one at random
    //     int chosenIndex = new Random().nextInt(potentialMatches.size());
    //     Request chosenMatch = potentialMatches.get(chosenIndex);

    //     // Step 4: Update isMatched status for both requests
    //     reqRepo.updateRequestMatchStatus(true, request.getRequestId());
    //     reqRepo.updateRequestMatchStatus(true, chosenMatch.getRequestId());

    //     // Step 5: Create new Pairing
    //     Pairing newPairing = new Pairing();
    //     newPairing.setEmployeeId(request.getEmployeeId());
    //     newPairing.setPairedEmployeeId(chosenMatch.getEmployeeId());
    //     newPairing.setPairingDate(LocalDate.now());
    //     newPairing.setLunchDate(request.getPreferredDate());
    //     newPairing.setLunchTime(request.getPreferredTime());
    //     newPairing.setLunchVenue("Office Cafe");

    //     // Create pairingId
    //     String lastPairingId = pairingRepo.getLastPairingId();
    //     String newPairingId;
    //     if (lastPairingId != null) {
    //         int lastSerialNumber = Integer.parseInt(lastPairingId.substring(lastPairingId.lastIndexOf('P') + 1));
    //         int newSerialNumber = lastSerialNumber + 1;
    //         newPairingId = String.format("P%04d", newSerialNumber);
    //     } else {
    //         newPairingId = String.format("P%04d", 1);
    //     }
    //     newPairing.setPairingId(newPairingId);
    //     return pairingRepo.insertPairing(newPairing);

    // }

    @Transactional
    public Pairing matchAndCreatePairing(Request request) {
    logger.info("Attempting to match and create pairing for request with preferred date: {}", request.getPreferredDate());
    
    // Step 1: Get all unmatched requests
    List<Request> allUnmatchedRequests = reqRepo.getAllUnmatchedRequests();
    Collections.shuffle(allUnmatchedRequests); // Randomize unmatched requests
    logger.info("Unmatched requests: {}", allUnmatchedRequests);

    // Step 2: Find potential matches from all unmatched requests
    Optional<Request> optionalMatch = allUnmatchedRequests.stream()
        .filter(potentialMatch -> !potentialMatch.getEmployeeId().equals(request.getEmployeeId()))  // Not the same employee
        .filter(potentialMatch -> potentialMatch.getPreferredDate().equals(request.getPreferredDate())) // Same preferred date
        .filter(potentialMatch -> potentialMatch.getPreferredTime().equals(request.getPreferredTime())) // Same preferred time
        .filter(potentialMatch -> request.getPreferredGender().equals("No Preference") || 
                                  potentialMatch.getPreferredGender().equals(request.getPreferredGender())) // Gender preference matches
        // .filter(potentialMatch -> !pairingRepo.hasPairedBeforeInLastSixMonths(request.getEmployeeId(), potentialMatch.getEmployeeId()))  // Not paired in last six months
        .findFirst();  // Stop on the first match

    if(!optionalMatch.isPresent()) {
        logger.info("No match found");
        return null;  // No match found
    }

    // Step 3: Retrieve the chosenMatch
    Request chosenMatch = optionalMatch.get();

     // Step 4: Update isMatched status for both requests
    reqRepo.updateRequestMatchStatus(true, request.getRequestId());
    reqRepo.updateRequestMatchStatus(true, chosenMatch.getRequestId());

    // Step 5: Create new Pairing
    Pairing newPairing = new Pairing();
    newPairing.setEmployeeId(request.getEmployeeId());
    newPairing.setPairedEmployeeId(chosenMatch.getEmployeeId());
    newPairing.setPairingDate(LocalDate.now());
    newPairing.setLunchDate(request.getPreferredDate());
    newPairing.setLunchTime(request.getPreferredTime());
    newPairing.setLunchVenue("Office Cafe");

    // Create pairingId
    String lastPairingId = pairingRepo.getLastPairingId();
    String newPairingId;
    if (lastPairingId != null) {
        int lastSerialNumber = Integer.parseInt(lastPairingId.substring(lastPairingId.lastIndexOf('P') + 1));
        int newSerialNumber = lastSerialNumber + 1;
        newPairingId = String.format("P%04d", newSerialNumber);
    } else {
        newPairingId = String.format("P%04d", 1);
    }
    newPairing.setPairingId(newPairingId);
    return pairingRepo.insertPairing(newPairing);   
    }


}
