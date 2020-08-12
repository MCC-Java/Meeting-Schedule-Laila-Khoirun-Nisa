/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.services;

import com.MeetingSchedule.MeetingSchedule.entities.Employee;
import com.MeetingSchedule.MeetingSchedule.repositories.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laila
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getById(String id) {
        return employeeRepository.findById(id).get();
    }

    public String getpass(String name) {
        return employeeRepository.findById(name).get().getPassword();
    }

    public Integer getrole(String name) {
        return employeeRepository.findById(name).get().getRole();
    }

    public boolean checkusername(String name) {
        return employeeRepository.existsById(name);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public boolean getLogin(String name, String password) {
        boolean result = false;
        Employee employee = employeeRepository.findLogin(name, password);

        if (employee.getName() != null && employee.getPassword() != null) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void saveRegister(String id, String name, String password, int role, String departmentid, String teamid) {

        employeeRepository.saveregister(id, name, password, role, departmentid, teamid);

    }
    
    public Employee saveRegister1(Employee employee){
        return employeeRepository.save(employee);
    }
}
