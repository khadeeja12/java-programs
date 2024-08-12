import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class GymMember {
    private String name;
    private String membershipType;
    private String phoneNumber;
    private String dietPlan;

    public GymMember(String name, String membershipType, String phoneNumber, String dietPlan) {
        this.name = name;
        this.membershipType = membershipType;
        this.phoneNumber = phoneNumber;
        this.dietPlan = dietPlan;
    }

    public String getName() {
        return name;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDietPlan() {
        return dietPlan;
    }

    public void setDietPlan(String dietPlan) {
        this.dietPlan = dietPlan;
    }
}

public class GymManagementSystem2 extends JFrame implements ActionListener {
    private JTextField usernameField, passwordField, nameField, membershipTypeField, phoneField, dietPlanField, dateField, timeField;
    private JButton loginButton, registerButton, logoutButton, viewProfileButton, updateDietPlanButton, viewMembershipButton, bookButton;
    private JPanel panel, memberPanel;
    private CardLayout cardLayout;
    private Map<String, User> users;
    private ArrayList<GymMember> members;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private JTextField loginUsernameField; // Declare as instance variable
    private JPasswordField loginPasswordField;
    private String currentMemberUsername;

    public GymManagementSystem2() {
        setTitle("Gym Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        users = new HashMap<>();
        users.put("admin", new User("admin", "admin", "admin"));

        members = new ArrayList<>();

        panel = new JPanel();
        cardLayout = new CardLayout();
        panel.setLayout(cardLayout);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2)); // Adjust grid layout
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginPanel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField(); // Initialize loginUsernameField
        loginPanel.add(loginUsernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField(); // Initialize loginPasswordField
        loginPanel.add(loginPasswordField);
        loginPanel.add(new JLabel("")); // Empty label for spacing
        loginPanel.add(loginButton);

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(7, 2)); // Adjust grid layout
        nameField = new JTextField();
        membershipTypeField = new JTextField();
        phoneField = new JTextField();
        dietPlanField = new JTextField();
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registrationPanel.add(new JLabel("Name:"));
        registrationPanel.add(nameField);
        registrationPanel.add(new JLabel("Membership Type:"));
        registrationPanel.add(membershipTypeField);
        registrationPanel.add(new JLabel("Phone Number:"));
        registrationPanel.add(phoneField);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        registrationPanel.add(new JLabel("Username:")); // Add label for username
        registrationPanel.add(usernameField); // Add username field
        registrationPanel.add(new JLabel("Password:")); // Add label for password
        registrationPanel.add(passwordField); // Add password field
        registrationPanel.add(new JLabel("Diet Plan:"));
        registrationPanel.add(dietPlanField);
        registrationPanel.add(registerButton);

        panel.add(loginPanel, "login");
        panel.add(registrationPanel, "register");
        cardLayout.show(panel, "register"); // Show registration panel by default

        add(panel);

        setVisible(true);

        connectToDatabase();
        createTables(); // Call the method to create necessary tables in the database
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym?characterEncoding=utf8", "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(50) NOT NULL," +
                    "role VARCHAR(20) NOT NULL)";
            statement.executeUpdate(createUserTableQuery);

            String createMembersTableQuery = "CREATE TABLE IF NOT EXISTS members (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "membership_type VARCHAR(50) NOT NULL," +
                    "phone_number VARCHAR(20) NOT NULL," +
                    "diet_plan VARCHAR(500))";
            statement.executeUpdate(createMembersTableQuery);

            // Creating the booking table
            String createBookingTableQuery = "CREATE TABLE IF NOT EXISTS booking (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "member_username VARCHAR(50) NOT NULL," +
                    "booking_date DATE NOT NULL," +
                    "booking_time TIME NOT NULL)";
            statement.executeUpdate(createBookingTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = loginUsernameField.getText(); // Use loginUsernameField
            String password = new String(loginPasswordField.getPassword()); // Use loginPasswordField

            try {
                String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
                preparedStatement = connection.prepareStatement(loginQuery);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");

                    if (resultSet.getString("role").equals("admin")) {
                        // Show admin interface
                        // Implement admin functionalities
                    } else {
                        // Show member interface
                        showMemberInterface(username);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String membershipType = membershipTypeField.getText();
            String phoneNumber = phoneField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword()); // Get password
            String dietPlan = dietPlanField.getText();

            try {
                String registerQuery = "INSERT INTO members (name, membership_type, phone_number, username, password, diet_plan) VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(registerQuery);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, membershipType);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, dietPlan);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(panel, "login");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == bookButton) {
            // Retrieve the selected date and time
            String date = dateField.getText();
            String time = timeField.getText();

            // Get the username of the current logged-in member
            String memberUsername = currentMemberUsername;

            try {
                // Prepare the query to insert booking details into the database
                String bookQuery = "INSERT INTO booking (member_username, booking_date, booking_time) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(bookQuery);
                preparedStatement.setString(1, memberUsername);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, time);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Booking successful!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showMemberInterface(String username) {
        currentMemberUsername = username; // Store the current member's username

        JFrame memberFrame = new JFrame("Member Interface - " + username);
        memberFrame.setSize(600, 400);
        memberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayout(7, 2));

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField(); // Corrected: Use class-level variables
        JLabel timeLabel = new JLabel("Time (HH:MM:SS):");
        timeField = new JTextField(); // Corrected: Use class-level variables
        bookButton = new JButton("Book");
        bookButton.addActionListener(this);

        memberPanel.add(dateLabel);
        memberPanel.add(dateField);
        memberPanel.add(timeLabel);
        memberPanel.add(timeField);
        memberPanel.add(bookButton);

        memberFrame.add(memberPanel);
        memberFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GymManagementSystem2();
    }
}
