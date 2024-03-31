package com.example.jdbc_assignment;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {

        // JDBC URL, username, and password
        static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeedb";
        static final String USERNAME = "root";
        static final String PASSWORD = "Harry@01";

        // Establish database connection
        static Connection connection = null;

        public static void main(String[] args) {
            try {
                // Register MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection to the database
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database.");

                // Create EmployeeManagementSystem object
                EmployeeManagementSystem empManagementSystem = new EmployeeManagementSystem();

                // Display menu and handle user input
                while (true) {
                    empManagementSystem.displayMenu();
                    Scanner scanner = new Scanner(System.in);
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline character

                    switch (choice) {
                        case 1:
                            empManagementSystem.addEmployee();
                            break;
                        case 2:
                            empManagementSystem.updateEmployee();
                            break;
                        case 3:
                            empManagementSystem.deleteEmployee();
                            break;
                        case 4:
                            empManagementSystem.displayEmployees();
                            break;
                        case 5:
                            System.out.println("Exiting the program.");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                        System.out.println("Disconnected from the database.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Method to display menu options
        public void displayMenu() {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add new employee");
            System.out.println("2. Update employee details");
            System.out.println("3. Delete an employee");
            System.out.println("4. Display all employees");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
        }

        // Method to add a new employee
        public void addEmployee() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter employee ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // consume newline character
                System.out.print("Enter employee name: ");
                String name = scanner.nextLine();
                System.out.print("Enter employee department: ");
                String department = scanner.nextLine();
                System.out.print("Enter employee salary: ");
                double salary = scanner.nextDouble();

                String sql = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, id);
                    statement.setString(2, name);
                    statement.setString(3, department);
                    ((PreparedStatement) statement).setDouble(4, salary);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Employee added successfully.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to update employee details
        public void updateEmployee() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter employee ID to update: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // consume newline character
                System.out.print("Enter new employee name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new employee department: ");
                String department = scanner.nextLine();
                System.out.print("Enter new employee salary: ");
                double salary = scanner.nextDouble();

                String sql = "UPDATE employees SET name=?, department=?, salary=? WHERE id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, department);
                    statement.setDouble(3, salary);
                    statement.setInt(4, id);
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Employee details updated successfully.");
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to delete an employee
        public void deleteEmployee() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter employee ID to delete: ");
                int id = scanner.nextInt();

                String sql = "DELETE FROM employees WHERE id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, id);
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Employee deleted successfully.");
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to display all employees
        public void displayEmployees() {
            try {
                String sql = "SELECT * FROM employees";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    System.out.println("Employee Records:");
                    System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "Name", "Department", "Salary");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String department = resultSet.getString("department");
                        double salary = resultSet.getDouble("salary");
                        System.out.printf("%-5d %-20s %-20s %-10.2f%n", id, name, department, salary);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
