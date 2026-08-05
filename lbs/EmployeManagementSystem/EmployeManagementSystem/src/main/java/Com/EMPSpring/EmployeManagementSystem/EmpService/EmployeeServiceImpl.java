package Com.EMPSpring.EmployeManagementSystem.EmpService;

import Com.EMPSpring.EmployeManagementSystem.EMPRepository.EmployeeRepository;
import Com.EMPSpring.EmployeManagementSystem.EmpEntity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements  EmployeeService {

    @Autowired
  private   EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }



    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

         if(existingEmployee!= null){
             existingEmployee.setName(employee.getName());
             existingEmployee.setDepartment(employee.getDepartment());
             existingEmployee.setSalary(employee.getSalary());

                return employeeRepository.save(existingEmployee);
         }
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);

    }
}
