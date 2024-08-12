import java.sql.*;

public class DisplayEmployeeDetails {
    static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    static final String USERNAME = "system";
    static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            CallableStatement cs = con.prepareCall("{call get_employee_details_cursor}");
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

         System.out.println("ResultSet Metadata:");
           for (int i = 1; i <= columnCount; i++) {
            System.out.println("Column " + i + ": " + metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i) + ")");
            }

            if (rs != null) {
                while (rs.next()) {
                    String ename = rs.getString("ename");
                    double esal = rs.getDouble("esal");
                    System.out.println("Employee Name: " + ename + ", Salary: " + esal);
                }

                System.out.println("Employee details displayed successfully");

                rs.close();
            } else {
                System.out.println("No records found.");
            }

            cs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
