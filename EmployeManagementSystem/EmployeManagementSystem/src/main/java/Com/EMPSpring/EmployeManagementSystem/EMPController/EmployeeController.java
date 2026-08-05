package Com.EMPSpring.EmployeManagementSystem.EMPController;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.EMPSpring.EmployeManagementSystem.EmpEntity.Employee;
import Com.EMPSpring.EmployeManagementSystem.EmpService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {

        return employeeService.addEmployee(employee);

    }




    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);

    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id,@RequestBody Employee employee){
        return employeeService.updateEmployee(id,employee);
    }


    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }


}
