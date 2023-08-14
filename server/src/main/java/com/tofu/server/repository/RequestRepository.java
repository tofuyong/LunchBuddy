package com.tofu.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tofu.server.models.Request;

@Repository
public class RequestRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_UNMATCHED_REQUESTS = "SELECT * from request where isMatched = false";
    private static final String GET_ALL_REQUESTS_BY_EMPLOYEE_ID_SQL = "SELECT * from request where employeeId = ?";
    private static final String GET_ALL_OPEN_REQUESTS_BY_EMPLOYEE_ID_SQL = "SELECT * from request where isMatched = false AND employeeId = ?";
    private static final String GET_REQUEST_BY_ID_SQL = "SELECT * from request where requestId = ?";
    private static final String INSERT_REQUEST_SQL = """ 
                                                    INSERT INTO request 
                                                    (requestId, preferredDate, preferredTime, preferredGender, employeeId)
                                                    VALUES (?, ?, ?, ?, ?)""";
    private static final String UPDATE_REQUEST_SQL = """ 
                                                    UPDATE request SET preferredDate = ?, preferredTime = ?, preferredGender = ?
                                                    WHERE requestId = ?""";
    private static final String UPDATE_REQUEST_MATCH_STATUS_SQL = """ 
                                                    UPDATE request SET isMatched = ?
                                                    WHERE requestId = ?""";
    private static final String DELETE_REQUEST_SQL = "DELETE FROM request WHERE requestId = ?";
    private static final String GET_LAST_REQUEST_ID_BY_EMPLOYEE_SQL = "SELECT requestId FROM request WHERE employeeId = ? ORDER BY requestId DESC LIMIT 1";

    public List<Request> getAllUnmatchedRequests() {
        return jdbcTemplate.query(GET_ALL_UNMATCHED_REQUESTS, BeanPropertyRowMapper.newInstance(Request.class));
    }

    public List<Request> getAllRequestsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_REQUESTS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Request.class), employeeId);
    }

    public List<Request> getAllOpenRequestsByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_OPEN_REQUESTS_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Request.class), employeeId);
    }

    public Request getRequest(String requestId) {
        return jdbcTemplate.queryForObject(GET_REQUEST_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Request.class), requestId);
    }

    public String insertRequest(Request request) {
        int iCreated = 0;  
        iCreated = jdbcTemplate.update(INSERT_REQUEST_SQL, request.getRequestId(), request.getPreferredDate(), request.getPreferredTime(),
                    request.getPreferredGender(), request.getEmployeeId());
        return iCreated > 0 ? request.getRequestId() : null;
    }
    
    public Boolean updateRequest(Request request, String requestId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_REQUEST_SQL, request.getPreferredDate(), request.getPreferredTime(), request.getPreferredGender(), 
            requestId); 
        return iResult > 0 ? true : false;
    }

    public Boolean updateRequestMatchStatus(Boolean isMatched, String requestId) {
        int iResult = jdbcTemplate.update(UPDATE_REQUEST_MATCH_STATUS_SQL, isMatched, requestId);
        return iResult > 0;
    }

    public Boolean deleteRequest(String requestId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_REQUEST_SQL, requestId);
        return iResult > 0 ? true : false;
    }

    public String getLastRequestIdByEmployeeId(Integer employeeId) {
        try {
            return jdbcTemplate.queryForObject(GET_LAST_REQUEST_ID_BY_EMPLOYEE_SQL, String.class, employeeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
