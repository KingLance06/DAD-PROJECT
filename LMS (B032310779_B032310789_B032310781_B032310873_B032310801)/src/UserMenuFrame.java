import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import org.json.*;

public class UserMenuFrame extends JFrame {

    public UserMenuFrame() {
        
        setTitle("📚 User Menu - Library System");
        setSize(420, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null); // Absolute layout
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        setupComponents(panel);
        setVisible(true);
    }

    private void setupComponents(JPanel panel) {
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel titleLabel = new JLabel("User Menu");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBounds(150, 20, 200, 30);
        panel.add(titleLabel);

        // View Books Button
        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.setBounds(100, 70, 200, 40);
        styleButton(viewBooksButton, new Color(0, 123, 255));
        panel.add(viewBooksButton);

        // Borrow Book Button
        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.setBounds(100, 120, 200, 40);
        styleButton(borrowBookButton, new Color(40, 167, 69));
        panel.add(borrowBookButton);

        // Return Book Button
        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(100, 170, 200, 40);
        styleButton(returnBookButton, new Color(255, 193, 7));
        panel.add(returnBookButton);

        // Back Button
        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(100, 220, 200, 40);
        styleButton(backButton, new Color(108, 117, 125));
        panel.add(backButton);

        // --- Actions ---
        viewBooksButton.addActionListener(e -> viewBooks());
        borrowBookButton.addActionListener(e -> borrowBook());
        returnBookButton.addActionListener(e -> returnBook());
        backButton.addActionListener(e -> {
            new LoginFrame();
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

    private void viewBooks() {
        try {
            JSONObject response = DatabaseConnection.getBooks();
            if (response.getBoolean("success")) {
                JSONArray booksArray = response.getJSONArray("books");
                StringBuilder bookDetails = new StringBuilder();
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject book = booksArray.getJSONObject(i);
                    bookDetails.append("Title: ").append(book.getString("title")).append("\n");
                    bookDetails.append("Author: ").append(book.getString("author")).append("\n");
                    bookDetails.append("Year: ").append(book.getInt("year")).append("\n");
                    bookDetails.append("Available: ").append(book.getBoolean("available") ? "Yes" : "No").append("\n");
                    bookDetails.append("--------------------------------------------------\n");
                }
                JTextArea textArea = new JTextArea(bookDetails.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Available Books", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch books.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrowBook() {
        String bookTitle = JOptionPane.showInputDialog(this, "Enter the title of the book to borrow:");
        if (bookTitle != null && !bookTitle.trim().isEmpty()) {
            try {
                JSONObject response = DatabaseConnection.borrowBook(bookTitle.trim());
                if (response.getBoolean("success")) {
                    JOptionPane.showMessageDialog(this, "Book borrowed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed: " + response.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnBook() {
        String bookTitle = JOptionPane.showInputDialog(this, "Enter the title of the book to return:");
        if (bookTitle != null && !bookTitle.trim().isEmpty()) {
            try {
                JSONObject response = DatabaseConnection.returnBook(bookTitle.trim());
                if (response.getBoolean("success")) {
                    JOptionPane.showMessageDialog(this, "Book returned successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed: " + response.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserMenuFrame::new);
    }
}
