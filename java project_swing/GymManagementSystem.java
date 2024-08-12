import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class GymManagementSystem extends JFrame implements ActionListener {
    private JTextField usernameField, passwordField, nameField, membershipTypeField, phoneField, dietPlanField;
    private JButton loginButton, registerButton, bookClassButton, addDietButton, scheduleAttendanceButton, viewBookingsButton;
    private JPanel panel;
    private CardLayout cardLayout;
    private Map<String, User> users;
    private ArrayList<GymMember> members;

    public GymManagementSystem() {
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
        loginPanel.setLayout(new GridLayout(3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(6, 2));
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
        registrationPanel.add(new JLabel("Diet Plan:"));
        registrationPanel.add(dietPlanField);
        registrationPanel.add(registerButton);

        panel.add(loginPanel, "login");
        panel.add(registrationPanel, "register");
        cardLayout.show(panel, "login");

        add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                if (users.get(username).getRole().equals("admin")) {
                    // Show admin interface
                    // Implement admin functionalities
                } else {
                    // Show member interface
                    showMemberInterface(username);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        } else if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String membershipType = membershipTypeField.getText();
            String phoneNumber = phoneField.getText();
            String dietPlan = dietPlanField.getText();

            members.add(new GymMember(name, membershipType, phoneNumber, dietPlan));

            JOptionPane.showMessageDialog(this, "Registration successful!");
            cardLayout.show(panel, "login");
        }
    }

    private void showMemberInterface(String username) {
        JFrame memberFrame = new JFrame("Member Interface - " + username);
        memberFrame.setSize(600, 400);
        memberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayout(5, 1));

        bookClassButton = new JButton("Book Class");
        addDietButton = new JButton("Add Diet");
        scheduleAttendanceButton = new JButton("Schedule Attendance");
        viewBookingsButton = new JButton("View Bookings");

        bookClassButton.addActionListener(this);
        addDietButton.addActionListener(this);
        scheduleAttendanceButton.addActionListener(this);
        viewBookingsButton.addActionListener(this);

        memberPanel.add(bookClassButton);
        memberPanel.add(addDietButton);
        memberPanel.add(scheduleAttendanceButton);
        memberPanel.add(viewBookingsButton);

        memberFrame.add(memberPanel);
        memberFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GymManagementSystem();
    }
}
