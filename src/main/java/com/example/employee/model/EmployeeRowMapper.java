package com.example.employee.model;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.employee.model.Employee;
public class EmployeeRowMapper implements RowMapper<Employee>{
    @Override
    public Employee mapRow(ResultSet rs,int rowNum) throws SQLException{
        return new Employee(
            rs.getInt("EMPLOYEEID"),
            rs.getString("EMPLOYEENAME"),
            rs.getString("EMAIL"),
            rs.getString("DEPARTMENT")
        );
    }
}
