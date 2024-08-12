import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton[] digitButtons;
    private JButton addButton, subtractButton, multiplyButton, divideButton, moduloButton, equalsButton;
    private double num1, num2, result;
    private char operator;

    public SimpleCalculator() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        // Text Field
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(textField, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        // Digit Buttons
        digitButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = new JButton(String.valueOf(i));
            digitButtons[i].addActionListener(this);
            digitButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            buttonPanel.add(digitButtons[i]);
        }

        // Operator Buttons
        addButton = createButton("+");
        subtractButton = createButton("-");
        multiplyButton = createButton("*");
        divideButton = createButton("/");
        moduloButton = createButton("%");
        equalsButton = createButton("=");
        
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(moduloButton);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.WHITE);
        return button;
    }

    @Override
public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.matches("[0-9]")) {
        textField.setText(textField.getText() + command);
    } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/") || command.equals("%")) {
        num1 = Double.parseDouble(textField.getText());
        operator = command.charAt(0);
        textField.setText(textField.getText() + " " + operator + " ");
    } else if (command.equals("=")) {
        num2 = Double.parseDouble(textField.getText().substring(textField.getText().indexOf(operator) + 2));
        try {
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new ArithmeticException("Cannot divide by zero!");
                    }
                    result = num1 / num2;
                    break;
                case '%':
                    result = num1 % num2;
                    break;
            }
            textField.setText(textField.getText() + " = " + result);
        } catch (ArithmeticException ex) {
            textField.setText("Error: " + ex.getMessage());
        }
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calculator = new SimpleCalculator();
            calculator.setVisible(true);
        });
    }
}
