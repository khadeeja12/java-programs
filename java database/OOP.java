 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OOP {
    private Connection connection;

    // Constructor to initialize the DatabaseConnector object
    public OOP(String url, String username, String password) {
        try {
            // Establish the database connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.out.println("Failed to establish database connection.");
            e.printStackTrace();
        }
    }

    // Method to execute a query and return the result set
    public ResultSet executeQuery(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + query);
            e.printStackTrace();
            return null;
        }
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection.");
            e.printStackTrace();
        }
    }

    // Example method to demonstrate usage
    public void retrieveData() {
        String query = "SELECT * FROM emp";
        ResultSet resultSet = executeQuery(query);
        try {
            // Process the result set
            while (resultSet != null && resultSet.next()) {
                int eno = resultSet.getInt("eno");
                String ename = resultSet.getString("ename");
                double esal = resultSet.getDouble("esal");
                
                
                // Example: Print retrieved data
                System.out.println("Employee Number: " + eno);
                System.out.println("Employee Name: " + ename);
                System.out.println("Employee Salary: " + esal);

            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving data.");
            e.printStackTrace();
        }
    }
    

    // Example main method
    // Example main method
public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/java_record?characterEncoding=UTF-8";
    String username = "root";
    String password = "";

    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println("Could not load MySQL JDBC driver.");
        e.printStackTrace();
        return;
    }

    OOP connector = new OOP(url, username, password);
    connector.retrieveData(); // Example: retrieve data from the database
    connector.closeConnection();
}
}
