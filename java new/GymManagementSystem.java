import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class GymMember implements Serializable {
    private String name;
    private int age;
    private String membershipType;

    public GymMember(String name, int age, String membershipType) {
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Membership Type: " + membershipType;
    }
}

public class GymManagementSystem extends JFrame implements ActionListener {
    private ArrayList<GymMember> members;
    private JTextArea displayArea;
    private JTextField nameField, ageField, membershipField;

    public GymManagementSystem() {
        members = new ArrayList<>();

        setTitle("Gym Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        JLabel membershipLabel = new JLabel("Membership Type:");
        membershipField = new JTextField();
        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(this);
        JButton deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(this);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(membershipLabel);
        inputPanel.add(membershipField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        loadMembersFromFile(); // Load members from file when program starts
        displayMembers();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Member")) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String membershipType = membershipField.getText();

            GymMember member = new GymMember(name, age, membershipType);
            members.add(member);
            saveMembersToFile(); // Save members to file after adding
            displayMembers();
        } else if (e.getActionCommand().equals("Delete Member")) {
            String nameToDelete = JOptionPane.showInputDialog("Enter name of member to delete:");
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(nameToDelete)) {
                    members.remove(i);
                    saveMembersToFile(); // Save members to file after deleting
                    displayMembers();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Member not found.");
        }
    }

    private void displayMembers() {
        StringBuilder sb = new StringBuilder();
        for (GymMember member : members) {
            sb.append(member.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void saveMembersToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("members.dat"))) {
            outputStream.writeObject(members);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadMembersFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("members.dat"))) {
            members = (ArrayList<GymMember>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file not found or error reading from file, initialize with an empty ArrayList
            members = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GymManagementSystem system = new GymManagementSystem();
                system.setVisible(true);
            }
        });
    }
}
