package se.lu.ics.controllers;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.lu.ics.data.DaoException;
import se.lu.ics.data.EmployeeDao;
import se.lu.ics.models.Employee;

public class EmployeesViewController {

    @FXML
    private TableView<Employee> tableViewEmployee;
    @FXML
    private TableColumn<Employee, String> tableColumnEmployeeNumber;
    @FXML
    private TableColumn<Employee, String> tableColumnEmployeeName;
    @FXML
    private TableColumn<Employee, Double> tableColumnEmployeeSalary;
    @FXML
    private TextField textFieldEmployeeNumber;
    @FXML
    private TextField textFieldEmployeeName;
    @FXML
    private TextField textFieldEmployeeSalary;
    @FXML
    private Button btnEmployeeAdd;
    @FXML
    private Label labelErrorMessage;
    private EmployeeDao employeeDao;

    public EmployeesViewController() {
        try {
            employeeDao = new EmployeeDao();
        } catch (IOException e) {
            displayErrorMessage("Error initializing database connection: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        tableColumnEmployeeNumber.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));
        tableColumnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        loadEmployees();
    }

 @FXML
    private void buttonEmployeeAdd_OnClick(MouseEvent event) {
        clearErrorMessage();

        try {
            // Collect employee data from text fields
            String empNo = textFieldEmployeeNumber.getText(); 
            String empName = textFieldEmployeeName.getText();
            double empSalary = Double.parseDouble(textFieldEmployeeSalary.getText());

            Employee newEmployee = new Employee(empNo, empName, empSalary);

            // Save the employee using the DAO
            employeeDao.save(newEmployee);

            // Refresh the TableView after saving
            loadEmployees();

            // Clear input fields after adding
            textFieldEmployeeNumber.clear(); 
            textFieldEmployeeName.clear();
            textFieldEmployeeSalary.clear();
        } catch (DaoException e) {
            displayErrorMessage("Error adding employee: Could not save employee to the database.");
        } catch (NumberFormatException e) {
            displayErrorMessage("Invalid salary. Please enter a valid number.");
        }
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeDao.getAll();
            ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
            tableViewEmployee.setItems(employeeList);
        } catch (DaoException e) {
            displayErrorMessage("Error loading employees: Could not retrieve employees from the database.");
        }
    }

    private void displayErrorMessage(String message) {
        labelErrorMessage.setText(message);
    }

    private void clearErrorMessage() {
        labelErrorMessage.setText("");
    }

}