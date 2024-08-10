import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Deletion {
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
        
        // Define the SQL query with a parameter
        String query = "DELETE FROM Emp WHERE esal < ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setDouble(1, 10000);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if any records were deleted
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " records deleted successfully.");
            } else {
                System.out.println("No records found with salary less than 10,000.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting records: " + e.getMessage());
        }
    }
}
