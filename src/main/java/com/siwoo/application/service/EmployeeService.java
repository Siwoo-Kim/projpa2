package com.siwoo.application.service;

import com.siwoo.application.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(int employeeId, String name, long salary);
    Employee findEmployee(int employeeId);
    List<Employee> findAllEmployees();
    Employee raiseEmployeeSalary(int employeeId, long raise);
    void removeEmployee(int employeeId);
}
