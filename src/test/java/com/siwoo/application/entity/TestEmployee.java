package com.siwoo.application.entity;


import com.siwoo.application.config.JpaConfig;
import com.siwoo.application.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class TestEmployee {

    @Autowired EmployeeService employeeService;

    @PersistenceUnit EntityManagerFactory entityManagerFactory;
    @PersistenceContext EntityManager entityManager;
    @Autowired PlatformTransactionManager transactionManager;
    @Test
    public void test(){
        TransactionDefinition definition = new DefaultTransactionDefinition();
        int employeeId = 158;
        //entityManager.getTransaction().begin();

        TransactionStatus status = transactionManager.getTransaction(definition);
        Employee emp = employeeService.createEmployee(employeeId,"Jon Doe",45000);
        assertNotNull(employeeService.findEmployee(emp.getId()));
        transactionManager.commit(status);
        //entityManager.getTransaction().commit();

        System.out.println("Persisted "+emp);

        //No transaction required
        emp = employeeService.findEmployee(employeeId);
        System.out.println("Found "+emp);

        List<Employee> employees = employeeService.findAllEmployees();
        employees.stream().map(Employee::toString).forEach(System.out::println);

        //entityManager.getTransaction().begin();
        status = transactionManager.getTransaction(definition);
        emp = employeeService.raiseEmployeeSalary(158,1000);
        transactionManager.commit(status);
        //entityManager.getTransaction().commit();
        System.out.println("Updated "+emp);

        assertEquals(employeeService.findEmployee(emp.getId()).getSalary(), 46000);

        //entityManager.getTransaction().begin();
        status = transactionManager.getTransaction(definition);
        employeeService.removeEmployee(158);
        transactionManager.commit(status);
        assertNull(employeeService.findEmployee(158));
        //entityManager.getTransaction().commit();
        System.out.println("Removed Employee 158");

        //Container automatically close factory and entity-manager resource
    }

}
