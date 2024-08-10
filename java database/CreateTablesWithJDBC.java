import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTablesWithJDBC {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/java_record?characterEncoding=UTF-8";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        // Dynamically load MySQL JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load MySQL JDBC driver.");
            e.printStackTrace();
            return;
        }

        // SQL statements to create tables
        String createDepartmentTableSQL = "CREATE TABLE Department ("
                + "dno INT PRIMARY KEY,"
                + "dname VARCHAR(255),"
                + "dloc VARCHAR(255)"
                + ")";
        
        String createEmpTableSQL = "CREATE TABLE Emp ("
                + "eno INT PRIMARY KEY,"
                + "ename VARCHAR(255),"
                + "esal DECIMAL(10, 2),"
                + "dno INT,"
                + "FOREIGN KEY (dno) REFERENCES Department(dno)"
                + ")";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {
            
            // Create Department table
            statement.executeUpdate(createDepartmentTableSQL);
            System.out.println("Department table created successfully.");

            // Create Emp table
            statement.executeUpdate(createEmpTableSQL);
            System.out.println("Emp table created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
