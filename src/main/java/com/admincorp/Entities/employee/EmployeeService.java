package com.admincorp.Entities.employee;

import java.time.LocalDateTime;
import java.util.List;
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
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (employee.isDeleted()) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }

        return employee;
    }

    // Select All
    public List<Employee> employeeFindAll() {
        Iterable<Employee> iterable = employeeRepository.findAllByDeletedFalse();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Update
    public Employee employeeUpdate(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (updatedEmployee.getCedula() != null) {
            existingEmployee.setCedula(updatedEmployee.getCedula());
        }
        if (updatedEmployee.getNombreEmpleado() != null) {
            existingEmployee.setNombreEmpleado(updatedEmployee.getNombreEmpleado());
        }
        if (updatedEmployee.getApellidoEmpleado() != null) {
            existingEmployee.setApellidoEmpleado(updatedEmployee.getApellidoEmpleado());
        }
        if (updatedEmployee.getCorreoEmpleado() != null) {
            existingEmployee.setCorreoEmpleado(updatedEmployee.getCorreoEmpleado());
        }

        return employeeRepository.save(existingEmployee);
    }

    // Delete (Logical)
    public void employeeDeleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        employee.setDeleted(true);
        employee.setDeleteAt(LocalDateTime.now());
        employeeRepository.save(employee);
    }
}