package com.tofu.server.repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tofu.server.models.Pairing;

@Repository
public class PairingRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_PAIRINGS_BY_EMPLOYEE_ID_SQL = "SELECT * from pairing where employeeId = ? OR pairedEmployeeId = ?";
    private static final String GET_ALL_ACCEPTED_PAIRINGS_BY_EMPLOYEE_ID_SQL = """
                                                                            SELECT * FROM pairing WHERE  
                                                                            pairedEmployeeAccepted = true AND employeeId = ? 
                                                                            OR pairedEmployeeAccepted = true AND pairedEmployeeId = ?""";
    private static final String GET_PENDING_LB_PAIRINGS_BY_EMPLOYEE_ID_SQL = """
                                                                            SELECT * FROM pairing WHERE employeeId = ? 
                                                                            AND pairedEmployeeAccepted = false""";
    private static final String GET_PENDING_YOUR_ACCEPTANCE_PAIRINGS_BY_EMPLOYEE_ID_SQL = """
                                                                                    SELECT * FROM pairing WHERE pairedEmployeeId = ? 
                                                                                    AND pairedEmployeeAccepted = false""";                                                                                                                                     
    private static final String GET_PAIRING_BY_ID_SQL = "SELECT * from pairing where pairingId = ?";
    private static final String INSERT_PAIRING_SQL = """
                                                    INSERT INTO pairing (pairingId, employeeId, pairedEmployeeId, 
                                                    pairingDate, lunchDate, lunchTime, lunchVenue) 
                                                    VALUES (?, ?, ?, ?, ?, ?, ?)""";
    private static final String UPDATE_EMPLOYEE_ACCEPTED_SQL = "UPDATE pairing SET employeeAccepted = ? WHERE pairingId = ?";
    private static final String UPDATE_PAIRED_EMPLOYEE_ACCEPTED_SQL = "UPDATE pairing SET pairedEmployeeAccepted = ? WHERE pairingId = ?";
    private static final String DELETE_PAIRING_SQL = "DELETE FROM pairing WHERE pairingId = ?";
    private static final String GET_LAST_PAIRING_ID = "SELECT pairingId FROM pairing ORDER BY pairingId DESC LIMIT 1";
    private static final String PAIRINGS_IN_LAST_SIX_MONTHS = "SELECT * FROM pairing WHERE (employeeId = ? AND pairedEmployeeId = ? OR employeeId = ? AND pairedEmployeeId = ?) AND pairingDate >= ?";

    public List<Pairing> getAllPairingsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_PAIRINGS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Pairing.class), employeeId, employeeId);
    }

    public List<Pairing> getAllAcceptedPairingsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_ACCEPTED_PAIRINGS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Pairing.class), employeeId, employeeId);
    }

    public List<Pairing> getPendingLBPairingsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_PENDING_LB_PAIRINGS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Pairing.class), employeeId);
    }

     public List<Pairing> getPendingYourAcceptancePairingsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_PENDING_YOUR_ACCEPTANCE_PAIRINGS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Pairing.class), employeeId);
    }

    public Pairing getPairing(String pairingId) {
        return jdbcTemplate.queryForObject(GET_PAIRING_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Pairing.class), pairingId);
    }

    public Pairing insertPairing(Pairing pairing) {
        int iCreated = 0;  
        iCreated = jdbcTemplate.update(INSERT_PAIRING_SQL, pairing.getPairingId(), pairing.getEmployeeId(), pairing.getPairedEmployeeId(),
                                        pairing.getPairingDate(), pairing.getLunchDate(), pairing.getLunchTime(), pairing.getLunchVenue());
        return iCreated > 0 ? pairing : null;
    }
    
    public Boolean updatePairingEmployeeAccepted(Boolean employeeAccepted, String pairingId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_EMPLOYEE_ACCEPTED_SQL, employeeAccepted, pairingId); 
        return iResult > 0 ? true : false;
    }

    public Boolean updatePairingPairedEmployeeAccepted(Boolean pairedEmployeeAccepted, String pairingId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_PAIRED_EMPLOYEE_ACCEPTED_SQL, pairedEmployeeAccepted, pairingId); 
        return iResult > 0 ? true : false;
    }

    public Boolean deletePairing(String pairingId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_PAIRING_SQL, pairingId);
        return iResult > 0 ? true : false;
    }

    public String getLastPairingId() {
        try {
            return jdbcTemplate.queryForObject(GET_LAST_PAIRING_ID, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean hasPairedBeforeInLastSixMonths(Integer employeeId1, Integer employeeId2) {
        List<Pairing> pairs = jdbcTemplate.query(PAIRINGS_IN_LAST_SIX_MONTHS, BeanPropertyRowMapper.newInstance(Pairing.class), 
                                                employeeId1, employeeId2, employeeId2, employeeId1, 
                                                LocalDate.now().minus(6, ChronoUnit.MONTHS));
        return !pairs.isEmpty();
    }

}
