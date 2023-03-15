package com.example.employee.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeRowMapper;
@Service
public class EmployeeH2Service implements EmployeeRepository{
    @Autowired
    private JdbcTemplate db;
    public ArrayList<Employee> getEmployee(){
        List<Employee> employeeList=db.query("select * from employeelist",new EmployeeRowMapper());
        ArrayList<Employee> employees=new ArrayList<>(employeeList);
        return employees;
    }
    public Employee getEmployeeById(int employeeId){
        try{
            Employee employee=db.queryForObject("select * from employeelist where employeeid=?", new EmployeeRowMapper(),employeeId);
            return employee;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
    }
    public Employee addEmployee(Employee employee){
        db.update("insert into employeelist(employeename,email,department) values(? ,?, ?)",employee.getEmployeeName(),employee.getEmail(),employee.getDepartment());
        Employee savedEmployee=db.queryForObject("select * from employeelist where employeeName=? and email=? and department=?",new EmployeeRowMapper(),employee.getEmployeeName(),employee.getEmail(),employee.getDepartment() );
        return savedEmployee;
    }
    public Employee updateEmployee(int employeeId,Employee employee){
        if(employee.getEmployeeName()!=null){
            db.update("update employeelist set employeename=? where employeeid=?",employee.getEmployeeName(),employeeId);
        }
        if(employee.getEmail()!=null){
            db.update("update employeelist set email=? where employeeid=?",employee.getEmail(),employeeId);
        }
        if(employee.getDepartment()!=null){
            db.update("update employeelist set department=? where employeeid=?",employee.getDepartment(),employeeId);
        }
        return getEmployeeById(employeeId);
    }
}