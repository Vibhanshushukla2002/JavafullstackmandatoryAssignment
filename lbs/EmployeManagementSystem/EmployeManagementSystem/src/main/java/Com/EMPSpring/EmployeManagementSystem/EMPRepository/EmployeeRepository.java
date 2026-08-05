package Com.EMPSpring.EmployeManagementSystem.EMPRepository;

import Com.EMPSpring.EmployeManagementSystem.EmpEntity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {


}
