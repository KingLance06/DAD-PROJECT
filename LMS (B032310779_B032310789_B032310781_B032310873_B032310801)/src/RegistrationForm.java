import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegistrationForm() {
        setTitle("Register - Library Management System");
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null); // Absolute layout
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        setupUI(panel);
        setVisible(true);
    }

    private void setupUI(JPanel panel) {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(40, 30, 120, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setBounds(160, 30, 200, 28);
        panel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(40, 75, 120, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBounds(160, 75, 200, 28);
        panel.add(passwordField);

        // Role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(labelFont);
        roleLabel.setBounds(40, 120, 120, 25);
        panel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[] { "user", "admin" });
        roleComboBox.setFont(fieldFont);
        roleComboBox.setBounds(160, 120, 200, 28);
        panel.add(roleComboBox);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(60, 190, 130, 40);
        styleButton(registerButton, new Color(72, 133, 237));
        panel.add(registerButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(220, 190, 130, 40);
        styleButton(backButton, new Color(220, 53, 69));
        panel.add(backButton);

        // Action Listeners
        registerButton.addActionListener((ActionEvent e) -> {
            try {
                registerUser();
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new Main();
            dispose();
        });
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void registerUser() throws IOException, JSONException {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JSONObject response = DatabaseConnection.register(username, password, role);
        if (response.getBoolean("success")) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();
            new Main(); // Return to main window
        } else {
            JOptionPane.showMessageDialog(this, response.getString("message"), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
