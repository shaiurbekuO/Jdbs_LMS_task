package java14;

import java14.config.DatabaseConfig;
import java14.enums.Position;
import java14.enums.Profession;
import java14.models.Employee;
import java14.models.Job;
import java14.service.EmployeeService;
import java14.service.JobService;
import java14.service.impl.EmployeeServiceImpl;
import java14.service.impl.JobServiceImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();
        employeeService.createEmployee();
//        jobService.createJobTable();
//        jobService.addJob(new Job(Position.MENTOR, Profession.JAVA,"Baekend",2));
//        System.out.println(jobService.getJobById(1L));
//        employeeService.addEmployee(new Employee("Omurbek", "Shaiyrbek uulu", 22, "Omurbek@gmail.com", 1));
//        employeeService.dropTable();
//        employeeService.cleanTable();
//        employeeService.updateEmployee(1L,new Employee("Omurbek", "Shaiyrbek uulu", 25, "Omurbek@gmail.com", 1));
//        System.out.println(employeeService.getAllEmployees());
//        System.out.println(employeeService.findByEmail("Omurbek@gmail.com"));
//        System.out.println(employeeService.getEmployeeByPosition("MENTOR"));
//        employeeService.getEmployeeByPosition(String.valueOf(Position.MENTOR));
    }
}
