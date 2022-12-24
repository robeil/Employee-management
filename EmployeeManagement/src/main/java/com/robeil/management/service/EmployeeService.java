package com.robeil.management.service;

import com.robeil.management.domain.Employee;
import com.robeil.management.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    void deleteEmployee(Long id);
    EmployeeDto getEmployee(Long id);
    void addEmployee(Employee employee);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto update(Long id, Employee employee);
    EmployeeDto getEmployeeByCode(String employeeCode);

}
