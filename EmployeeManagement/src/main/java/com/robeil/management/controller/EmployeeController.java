package com.robeil.management.controller;

import com.robeil.management.domain.Employee;
import com.robeil.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allEmployees() {
        var allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        var employee = employeeService.getEmployee(id);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/find/code/{employeeCode}")
    public ResponseEntity<?> getEmployee(@PathVariable String employeeCode) {
        var employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        var employeeToUpdate = employeeService.getEmployee(id);
        if (employeeToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var updated = employeeService.update(id, employee);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        var employeeToDelete = employeeService.getEmployee(id);
        if(employeeToDelete == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
