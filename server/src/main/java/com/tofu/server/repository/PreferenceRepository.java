package com.tofu.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tofu.server.models.Preference;

@Repository
public class PreferenceRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_PREFERENCES_BY_EMPLOYEE_ID_SQL = "SELECT * from preference where employeeId = ?";
    private static final String GET_PREFERENCE_BY_ID_SQL = "SELECT * from preference where preferenceId = ?";
    private static final String INSERT_PREFERENCE_SQL = """ 
                                                    INSERT INTO preference 
                                                    (preferenceId, preferredDate, preferredTime, preferredGender, employeeId)
                                                    VALUES (?, ?, ?, ?, ?)""";
    private static final String UPDATE_PREFERENCE_SQL = """ 
                                                    UPDATE preference SET preferredDate = ?, preferredTime = ?, preferredGender = ?
                                                    WHERE preferenceId = ?""";
    private static final String DELETE_PREFERENCE_SQL = "DELETE FROM preference WHERE preferenceId = ?";
    private static final String GET_LAST_PREFERENCE_ID_BY_EMPLOYEE_SQL = """
                                                                        SELECT preferenceId FROM preference 
                                                                        WHERE employeeId = ? ORDER BY preferenceId DESC LIMIT 1""";

    public List<Preference> getAllPreferencesByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query(GET_ALL_PREFERENCES_BY_EMPLOYEE_ID_SQL, BeanPropertyRowMapper.newInstance(Preference.class), employeeId);
    }

    public Preference getPreference(String preferenceId) {
        return jdbcTemplate.queryForObject(GET_PREFERENCE_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Preference.class), preferenceId);
    }

    public Boolean insertPreference(Preference preference) {
        int iCreated = 0;  
        iCreated = jdbcTemplate.update(INSERT_PREFERENCE_SQL, preference.getPreferenceId(), preference.getPreferredDate(), preference.getPreferredTime(),
                    preference.getPreferredGender(), preference.getEmployeeId());
        return iCreated > 0 ? true : false;
    }
    
    public Boolean updatePreference(Preference preference, String preferenceId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_PREFERENCE_SQL, preference.getPreferredDate(), preference.getPreferredTime(), preference.getPreferredGender(), 
            preferenceId); 
        return iResult > 0 ? true : false;
    }

    public Boolean deletePreference(String preferenceId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_PREFERENCE_SQL, preferenceId);
        return iResult > 0 ? true : false;
    }

    public String getLastPreferenceIdByEmployeeId(Integer employeeId) {
        try {
            return jdbcTemplate.queryForObject(GET_LAST_PREFERENCE_ID_BY_EMPLOYEE_SQL, String.class, employeeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
