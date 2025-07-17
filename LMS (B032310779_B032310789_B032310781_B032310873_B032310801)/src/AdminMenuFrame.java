import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminMenuFrame extends JFrame {

    public AdminMenuFrame() {
        
        setTitle("📚 Library Management System - Admin Menu");
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        JPanel panel = new JPanel(null); // Absolute layout
        panel.setBackground(new Color(250, 250, 250)); // Light background
        getContentPane().add(panel);

        placeComponents(panel); // Add UI components

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Color buttonColor = new Color(72, 133, 237); // Blue
        Color backButtonColor = new Color(220, 53, 69); // Red
        Color textColor = Color.WHITE;

        // ➕ Add Book Button
        JButton addBookButton = new JButton("Add Book");
        configureButton(addBookButton, buttonFont, buttonColor, textColor);
        addBookButton.setBounds(50, 40, 300, 50);
        panel.add(addBookButton);

        // 🗑 Remove Book Button
        JButton removeBookButton = new JButton("Remove Book");
        configureButton(removeBookButton, buttonFont, buttonColor, textColor);
        removeBookButton.setBounds(50, 110, 300, 50);
        panel.add(removeBookButton);

        // 👥 View Users Button
        JButton viewUsersButton = new JButton("View Users");
        configureButton(viewUsersButton, buttonFont, buttonColor, textColor);
        viewUsersButton.setBounds(50, 180, 300, 50);
        panel.add(viewUsersButton);

        // ⬅ Back Button
        JButton backButton = new JButton("Back to Login");
        configureButton(backButton, buttonFont, backButtonColor, textColor);
        backButton.setBounds(50, 250, 300, 50);
        panel.add(backButton);

        // --- Action Listeners ---
        addBookButton.addActionListener((ActionEvent e) -> {
            new AddBookFrame(); // Replace with your actual frame
            dispose();
        });

        removeBookButton.addActionListener((ActionEvent e) -> {
            new RemoveBookFrame();
            dispose();
        });

        viewUsersButton.addActionListener((ActionEvent e) -> {
            new ViewUsersFrame();
            dispose();
        });

        backButton.addActionListener((ActionEvent e) -> {
            new LoginFrame();   // Open login screen
            dispose();          // Close current frame
        });
    }

    private void configureButton(JButton button, Font font, Color bgColor, Color fgColor) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminMenuFrame::new);
    }
}
