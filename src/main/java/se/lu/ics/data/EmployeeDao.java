package se.lu.ics.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.lu.ics.models.Employee;

public class EmployeeDao {

    private ConnectionHandler connectionHandler;

    public EmployeeDao() throws IOException {
        this.connectionHandler = new ConnectionHandler();
    }

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee";

        try (Connection connection = connectionHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String empNo = resultSet.getString("EmpNo");
                String empName = resultSet.getString("EmpName");
                double empSalary = resultSet.getDouble("EmpSalary");
                employees.add(new Employee(empNo, empName, empSalary));
            }
        } catch (SQLException e) {
            throw new DaoException("Error retrieving employees", e);
        }
        return employees;
    }

    public void save(Employee employee) {
        // Misuse of PreparedStatement: variables concatenated directly into the query string
        String query = "INSERT INTO Employee (EmpNo, EmpName, EmpSalary) VALUES ("
                        + "'" + employee.getEmployeeNumber() + "', "
                        + "'" + employee.getName() + "', "
                        + employee.getSalary() + ")";
    
        try (Connection connection = connectionHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            // No parameters set using placeholders
            statement.executeUpdate();
        } catch (SQLException e) {
            // SQL Server-specific unique constraint violation error code
            if (e.getErrorCode() == 2627) {
                throw new DaoException("An employee with this Employee No already exists.", e);
            } else {
                throw new DaoException("Error saving employee: " + employee.getEmployeeNumber(), e);
            }
        }
    }
}
