package com.robeil.management.service.impl;

import com.robeil.management.domain.Employee;
import com.robeil.management.dto.EmployeeDto;
import com.robeil.management.exception.UserNotFoundException;
import com.robeil.management.repository.EmployeeRepository;
import com.robeil.management.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        var allEmployee = employeeRepository.findAll();

        return allEmployee.stream()
                .map(emp -> modelMapper.map(emp,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Long id) {
        var employeeToDelete = employeeRepository.findById(id);
        if (employeeToDelete.isEmpty()) {
            System.out.println("The employee with id " + id + " not found");
            log.info("The employee with id " + id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            System.out.println("The employee with id " + id + " not found");
            throw new UserNotFoundException("The employee with id " + id + " not found");
        }
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public void addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeByCode(String employeeCode) {
        var allEmployee = employeeRepository.findAll();
        return allEmployee.stream()
                .filter(emp -> emp.getEmployeeCode().equals(employeeCode))
                .findFirst()
                .map(dto ->  modelMapper.map(dto,EmployeeDto.class))
                .orElse(null);
    }

    @Override
    public EmployeeDto update(Long id, Employee employee) {
        var update = employeeRepository.findById(id).get();

        if(update != null){
            update.setId(id);
            update.setEmail(employee.getEmail());
            update.setEmployeeCode(employee.getEmployeeCode());
            update.setName(employee.getName());
            update.setPhone(employee.getPhone());
            update.setImageUrl(employee.getImageUrl());
            update.setJobTitle(employee.getJobTitle());
            employeeRepository.save(update);
        } else {
            System.out.println("The employee with id " + id + " not found");
            throw new UserNotFoundException("The employee with id " + id + " not found");
        }
        return modelMapper.map(update,EmployeeDto.class);
    }
}
