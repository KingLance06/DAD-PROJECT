import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("🔐 Login - Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
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

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(40, 40, 120, 25);
        panel.add(usernameLabel);

        // Username Field
        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setBounds(150, 40, 200, 28);
        panel.add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(40, 90, 120, 25);
        panel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBounds(150, 90, 200, 28);
        panel.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(40, 160, 100, 40);
        styleButton(loginButton, new Color(72, 133, 237));
        panel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 160, 100, 40);
        styleButton(registerButton, new Color(40, 167, 69));
        panel.add(registerButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(260, 160, 90, 40);
        styleButton(backButton, new Color(220, 53, 69));
        panel.add(backButton);

        // Action Listeners
        loginButton.addActionListener(e -> {
            try {
                authenticate();
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new RegistrationForm();
            dispose();
        });

        backButton.addActionListener(e -> {
            new Main();
            dispose();
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void authenticate() throws IOException, JSONException {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        JSONObject response = DatabaseConnection.login(username, password);
        if (response.getBoolean("success")) {
            String role = response.getString("role");
            JOptionPane.showMessageDialog(this, "Login Successful! Logged in as " + role);
            if (role.equalsIgnoreCase("admin")) {
                new AdminMenuFrame();
            } else {
                new UserMenuFrame();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, response.getString("message"), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
