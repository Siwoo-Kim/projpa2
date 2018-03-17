package com.siwoo.application.service;

import com.siwoo.application.entity.Employee;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext EntityManager entityManager;
    @Override
    public Employee createEmployee(int employeeId, String name, long salary) {
        Employee employee = new Employee(employeeId,name,salary);
        entityManager.persist(employee);
        return employee;
    }

    @Override
    public Employee findEmployee(int employeeId) {
        return entityManager.find(Employee.class,employeeId);
    }

    @Override
    public List<Employee> findAllEmployees() {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e",Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee raiseEmployeeSalary(int employeeId, long raise) {
        Employee employee = findEmployee(employeeId);
        if(employee != null) {
            employee.raiseSalary(raise);
        }
        return employee;
    }

    @Override
    public void removeEmployee(int employeeId) {
        Employee employee = findEmployee(employeeId);
        if(employee != null){
            entityManager.remove(employee);
        }
    }
}
