package com.tofu.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tofu.server.models.Employee;

@Repository
public class EmployeeRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String GET_EMPLOYEE_BY_ID_SQL = "SELECT * from employee where employeeId = ?";
    private static final String INSERT_EMPLOYEE_SQL = """
                                                    INSERT INTO employee (employeeId, isFinding, firstName, lastName, 
                                                    salutation, gender, email, department, title) 
                                                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String UPDATE_EMPLOYEE_SQL = """
                                                    UPDATE employee SET isFinding = ?, firstName = ?, lastName = ?, 
                                                    salutation = ?, gender = ?, email = ?, department = ?, title = ?
                                                    WHERE employeeId = ?""";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE employeeId = ?";
    private static final String UPDATE_EMPLOYEE_PHOTO_SQL = "UPDATE employee SET photo = ? WHERE employeeId = ?";
    private static final String DELETE_EMPLOYEE_PHOTO_SQL = "UPDATE employee SET photo = NULL WHERE employeeId = ?";
    private static final String GET_EMPLOYEES_FINDING_LB_SQL = "SELECT * FROM employee WHERE isFinding = true";

    public Employee getEmployee(Integer employeeId) {
        return jdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Employee.class), employeeId);
    }
    
    public Boolean insertEmployee(Employee employee) {
        int iCreated = 0;
        iCreated = jdbcTemplate.update(INSERT_EMPLOYEE_SQL, employee.getEmployeeId(), employee.getIsFinding(), employee.getFirstName(), 
            employee.getLastName(), employee.getSalutation(), employee.getGender(), employee.getEmail(),
            employee.getDepartment(), employee.getTitle());
        return iCreated > 0 ? true : false;
    }

    public Boolean updateEmployee(Employee employee, Integer employeeId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_EMPLOYEE_SQL, employee.getIsFinding(), employee.getFirstName(), 
            employee.getLastName(), employee.getSalutation(), employee.getGender(), employee.getEmail(),
            employee.getDepartment(), employee.getTitle(), employeeId);
        return iResult > 0 ? true : false;
    }

    public Boolean deleteEmployee(Integer employeeId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_EMPLOYEE_SQL, employeeId);
        return iResult > 0 ? true : false;
    }

    public Boolean updateEmployeePhoto(String photoBase64, Integer employeeId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(UPDATE_EMPLOYEE_PHOTO_SQL, photoBase64, employeeId);
        return iResult > 0 ? true : false;
    }

    public Boolean deleteEmployeePhoto(Integer employeeId) {
        int iResult = 0;
        iResult = jdbcTemplate.update(DELETE_EMPLOYEE_PHOTO_SQL, employeeId);
        return iResult > 0 ? true : false;
    }

    public List<Employee> getEmployeesFindingLunchBuddy() {
        return jdbcTemplate.query(GET_EMPLOYEES_FINDING_LB_SQL, BeanPropertyRowMapper.newInstance(Employee.class));
    }

}
