import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import org.json.*;

public class ViewUsersFrame extends JFrame {

    private JTextArea userDetailsArea;

    public ViewUsersFrame() {
    	setTitle("👥 View Users - Library System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null); // Absolute layout
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        setupComponents(panel);
        setVisible(true);
    }

    private void setupComponents(JPanel panel) {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel titleLabel = new JLabel("User Details");
        titleLabel.setFont(labelFont);
        titleLabel.setBounds(180, 20, 150, 25);
        panel.add(titleLabel);

        userDetailsArea = new JTextArea();
        userDetailsArea.setEditable(false);
        userDetailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(userDetailsArea);
        scrollPane.setBounds(40, 60, 400, 200);
        panel.add(scrollPane);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(90, 280, 120, 40);
        styleButton(refreshButton, new Color(0, 123, 255));
        panel.add(refreshButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(260, 280, 120, 40);
        styleButton(backButton, new Color(108, 117, 125));
        panel.add(backButton);

        refreshButton.addActionListener(e -> {
            try {
                String usersInfo = getUsersInfo();
                userDetailsArea.setText(usersInfo);
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching user details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new AdminMenuFrame(); // Return to admin menu
        });

        // Load data on start
        try {
            userDetailsArea.setText(getUsersInfo());
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching user details.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private String getUsersInfo() throws IOException, JSONException {
        JSONObject response = DatabaseConnection.getUsers();
        if (response.getBoolean("success")) {
            StringBuilder sb = new StringBuilder();
            JSONArray usersArray = response.getJSONArray("users");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                sb.append("User ID: ").append(user.getInt("id")).append("\n");
                sb.append("Username: ").append(user.getString("username")).append("\n");
                sb.append("Role: ").append(user.getString("role")).append("\n");
                sb.append("------------------------------\n");
            }
            return sb.toString();
        } else {
            return "No users found.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewUsersFrame::new);
    }
}
