import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveBookFrame extends JFrame {

    private JComboBox<String> bookList;

    public RemoveBookFrame() {
        setTitle("📕 Remove Book - Library System");
        setSize(400, 250);
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
        Font comboFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Label
        JLabel titleLabel = new JLabel("Select Book to Remove:");
        titleLabel.setFont(labelFont);
        titleLabel.setBounds(40, 40, 220, 25);
        panel.add(titleLabel);

        // Book List ComboBox
        bookList = new JComboBox<>();
        bookList.setFont(comboFont);
        bookList.setBounds(40, 75, 300, 30);
        panel.add(bookList);
        populateBooks();

        // Remove Button
        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(60, 140, 120, 40);
        styleButton(removeButton, new Color(220, 53, 69));
        panel.add(removeButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(210, 140, 120, 40);
        styleButton(backButton, new Color(108, 117, 125));
        panel.add(backButton);

        // Action Listeners
        removeButton.addActionListener((ActionEvent e) -> {
            String selectedBook = (String) bookList.getSelectedItem();
            if (selectedBook != null) {
                removeBook(selectedBook);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new AdminMenuFrame();
            dispose();
        });
    }

    private void populateBooks() {
        try {
            JSONObject response = DatabaseConnection.getBookTitles();
            if (response.getBoolean("success")) {
                JSONArray titlesArray = response.getJSONArray("titles");
                for (int i = 0; i < titlesArray.length(); i++) {
                    bookList.addItem(titlesArray.getString(i));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch book titles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeBook(String bookTitle) {
        try {
            JSONObject response = DatabaseConnection.deleteBook(bookTitle);
            if (response.getBoolean("success")) {
                JOptionPane.showMessageDialog(this, "Book removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookList.removeItem(bookTitle); // Remove from dropdown
            } else {
                JOptionPane.showMessageDialog(this, "Failed: " + response.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        SwingUtilities.invokeLater(RemoveBookFrame::new);
    }
}
