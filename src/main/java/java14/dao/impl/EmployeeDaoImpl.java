package java14.dao.impl;

import java14.config.DatabaseConfig;
import java14.dao.EmployeeDao;
import java14.models.Employee;
import java14.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao, AutoCloseable {
    private final Connection connection = DatabaseConfig.getConnection();
    @Override
    public void createEmployee() {
        String sql = """
        create table if not exists employees(
        id serial primary key,
        first_name varchar(50),
        last_name varchar(50),
        age int,
        email varchar(150),
        job_id int references job(id)
        )
        """;

        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Employee created");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees(first_name, last_name, age, email, job_id)
                values(?,?,?,?,?)
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
             preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "drop table if exists employees";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Employee dropped");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void cleanTable() {
        String sql = """
                truncate table employees
                """;
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Table employees truncated.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                update employees set
                first_name=?,
                last_name=?,
                age = ?,
                email = ?
                where id=?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, id);
            int rowsupdate = preparedStatement.executeUpdate();
            System.out.println(rowsupdate > 0);
            System.out.println("Employee  updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = "select * from employees";
        List<Employee> employees = new ArrayList<>();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if(!resultSet.next()) throw new RuntimeException("Employee not found ");
            while(resultSet.next()){
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = "select * from employees where email = ?";
        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) throw new RuntimeException("Employee not found "+email);
            employee.setId(resultSet.getLong("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setAge(resultSet.getInt("age"));
            employee.setEmail(resultSet.getString("email"));
            employee.setJobId(resultSet.getInt("job_id"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        String sql = """
            select e.id, e.first_name, e.last_name, e.age, e.email, e.job_id\s
                    from employees e\s
                    inner join job j on e.job_id = j.id\s
                    where j.position = ?
          """;
        List<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            }
            if (employees.isEmpty()) {
                throw new RuntimeException("Employee not found for position: " + position);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
