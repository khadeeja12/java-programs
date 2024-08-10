 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartR {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/java_record?characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load MySQL JDBC driver.");
            e.printStackTrace();
            return;
        }
        
        // Define the SQL query with parameters
        String query = "SELECT * FROM Emp WHERE ename LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setString(1, "R%");

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display the results
            System.out.println("Records with names starting with 'R':");
            while (resultSet.next()) {
                int eno = resultSet.getInt("eno");
                String ename = resultSet.getString("ename");
                double esal = resultSet.getDouble("esal");
                int dno = resultSet.getInt("dno");

                System.out.println("Employee Number: " + eno);
                System.out.println("Employee Name: " + ename);
                System.out.println("Employee Salary: " + esal);
                System.out.println("Department Number: " + dno);
                System.out.println("------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching records: " + e.getMessage());
        }
    }
}
 
    

