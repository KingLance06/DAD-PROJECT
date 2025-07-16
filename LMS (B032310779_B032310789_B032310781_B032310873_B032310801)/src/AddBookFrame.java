import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class AddBookFrame extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JCheckBox availableCheckBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddBookFrame::new);
    }

    public AddBookFrame() {
        setTitle("📚 Add New Book");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);
        initComponents(panel);

        setVisible(true);
    }

    private void initComponents(JPanel panel) {
        JLabel titleLabel = new JLabel("Title:");
        JLabel authorLabel = new JLabel("Author:");
        JLabel yearLabel = new JLabel("Year:");
        JLabel availableLabel = new JLabel("Available:");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        titleLabel.setFont(labelFont);
        authorLabel.setFont(labelFont);
        yearLabel.setFont(labelFont);
        availableLabel.setFont(labelFont);

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        yearField = new JTextField(4);
        availableCheckBox = new JCheckBox();
        availableCheckBox.setBackground(Color.WHITE);

        JButton addButton = new JButton("➕ Add Book");
        JButton backButton = new JButton("⬅ Back");

        // Styling Buttons
        addButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        addButton.setBackground(new Color(72, 133, 237));
        addButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);

        // Action Listeners
        addButton.addActionListener((ActionEvent e) -> {
            try {
                addBook();
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new AdminMenuFrame();
            dispose();
        });

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(titleLabel)
                    .addComponent(authorLabel)
                    .addComponent(yearLabel)
                    .addComponent(availableLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(titleField)
                    .addComponent(authorField)
                    .addComponent(yearField)
                    .addComponent(availableCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addComponent(backButton)))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(titleField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(authorLabel)
                    .addComponent(authorField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(availableLabel)
                    .addComponent(availableCheckBox))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(backButton))
        );
    }

    private void addBook() throws IOException, JSONException {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean available = availableCheckBox.isSelected();

        JSONObject response = DatabaseConnection.addBook(title, author, year, available);
        if (response.getBoolean("success")) {
            JOptionPane.showMessageDialog(this, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AdminMenuFrame();
        } else {
            JOptionPane.showMessageDialog(this, response.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
