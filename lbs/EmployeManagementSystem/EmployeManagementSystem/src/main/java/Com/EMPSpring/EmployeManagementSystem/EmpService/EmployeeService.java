package Com.EMPSpring.EmployeManagementSystem.EmpService;

import Com.EMPSpring.EmployeManagementSystem.EMPRepository.EmployeeRepository;
import Com.EMPSpring.EmployeManagementSystem.EmpEntity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface EmployeeService {

   // Employee addEmployee(Employee employee);

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);

    Employee updateEmployee(Integer id, Employee employee);

    void deleteEmployee(Integer id);


   // Employee addEmployee(Employee employee);
}
