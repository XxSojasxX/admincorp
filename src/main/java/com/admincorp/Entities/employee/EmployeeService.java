package com.admincorp.Entities.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("unused")
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Insert
    public Employee employeeSave(Employee entity) {
        return employeeRepository.save(entity);
    }

    // Select
    public Employee employeeFindById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        return employee;
    }

    // Select All
    public List<Employee> employeeFindAll() {
        Iterable<Employee> iterable = employeeRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Employee employeeUpdate(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        existingEmployee.setCedula(updatedEmployee.getCedula());
        existingEmployee.setNombreEmpleado(updatedEmployee.getNombreEmpleado());
        existingEmployee.setApellidoEmpleado(updatedEmployee.getApellidoEmpleado());
        existingEmployee.setCorreoEmpleado(updatedEmployee.getCorreoEmpleado());

        return employeeRepository.save(existingEmployee);
    }

    // Delete
    public void employeeDeleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        employeeRepository.deleteById(id);
    }
}