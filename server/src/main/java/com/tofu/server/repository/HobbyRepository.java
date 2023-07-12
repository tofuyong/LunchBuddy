package com.tofu.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tofu.server.models.Hobby;

@Repository
public class HobbyRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_HOBBIES_BY_EMPLOYEE_ID_SQL = "SELECT * from hobby where employeeId = ?";
    private static final String GET_HOBBY_BY_ID_SQL = "SELECT * from hobby where hobbyId = ?";
    private static final String INSERT_HOBBY_SQL = "INSERT INTO hobby (hobbyId, hobby, employeeId) VALUES (?, ?, ?)";
    private static final String UPDATE_HOBBY_SQL = "UPDATE hobby SET hobby = ? WHERE hobbyId = ?";
    private static final String DELETE_HOBBY_SQL = "DELETE FROM hobby WHERE hobbyId = ?";
    private static final String GET_LAST_HOBBY_ID_BY_EMPLOYEE_SQL = "SELECT hobbyId FROM hobby WHERE employeeId = ? ORDER BY hobbyId DESC LIMIT 1";

    public List<Hobby> getAllHobbiesByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_HOBBIES_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Hobby.class), employeeId);
    }

    public Hobby getHobby(String hobbyId) {
        return jdbcTemplate.queryForObject(GET_HOBBY_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Hobby.class), hobbyId);
    }

    public Boolean insertHobby(Hobby hobby) {
        int iCreated = 0;  
        iCreated = jdbcTemplate.update(INSERT_HOBBY_SQL, hobby.getHobbyId(), hobby.getHobby(), hobby.getEmployeeId());
        return iCreated > 0 ? true : false;
    }
    
    public Boolean updateHobby(Hobby hobby, String hobbyId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_HOBBY_SQL, hobby.getHobby(), hobbyId); 
        return iResult > 0 ? true : false;
    }

    public Boolean deleteHobby(String hobbyId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_HOBBY_SQL, hobbyId);
        return iResult > 0 ? true : false;
    }

    public String getLastHobbyIdByEmployeeId(Integer employeeId) {
        try {
            return jdbcTemplate.queryForObject(GET_LAST_HOBBY_ID_BY_EMPLOYEE_SQL, String.class, employeeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
}
