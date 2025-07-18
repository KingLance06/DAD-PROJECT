import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("📚 Library Management System");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null); // Absolute layout
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        setupComponents(panel);
        setVisible(true);
    }
        //components Setup
    private void setupComponents(JPanel panel) {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to Library System", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(40, 30, 340, 30);
        panel.add(titleLabel);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 100, 200, 40);
        styleButton(loginButton, new Color(0, 123, 255));
        panel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(110, 160, 200, 40);
        styleButton(registerButton, new Color(40, 167, 69));
        panel.add(registerButton);

        // Action Listeners
        loginButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        registerButton.addActionListener(e -> {
            new RegistrationForm();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
