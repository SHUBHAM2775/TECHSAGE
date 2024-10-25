import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TechSageSignIn extends JFrame {

    public TechSageSignIn() {
        setTitle("TechSage - Sign In");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1600, 900)); // Set preferred size for scrolling

        // Logo Image (Replace with actual logo path)
        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg"));
        logoLabel.setBounds(550, 100, 500, 350); // Adjust size and location as needed
        mainPanel.add(logoLabel);

        // Welcome Text
        JLabel welcomeLabel = new JLabel("Log into your account", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(625, 450, 350, 30);
        mainPanel.add(welcomeLabel);

        // Email Field
        JTextField emailField = new JTextField("Enter your email address");
        emailField.setBounds(650, 500, 300, 40);
        mainPanel.add(emailField);

        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(650, 550, 300, 40);
        mainPanel.add(passwordField);

        // Forgot Password Link
        JLabel forgotPasswordLabel = new JLabel("Forgot password?", SwingConstants.RIGHT);
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotPasswordLabel.setForeground(Color.WHITE);
        forgotPasswordLabel.setBounds(800, 600, 150, 20);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable
        mainPanel.add(forgotPasswordLabel);

        // Continue Button
        JButton continueButton = new JButton("Continue");
        continueButton.setBackground(new Color(128, 0, 255)); // Purple color
        continueButton.setForeground(Color.WHITE);
        continueButton.setBounds(650, 650, 300, 40);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Check for admin credentials
                if (email.equals("tsecadmin@gmail.com") && password.equals("admin123")) {
                    JOptionPane.showMessageDialog(null, "Admin Login Successful!", "Login", JOptionPane.INFORMATION_MESSAGE);

                    // Open Admin Dashboard
                    dispose(); // Close the sign-in window
                    AdminDashboard adminDashboard = new AdminDashboard(); // Assuming this is the Admin dashboard class
                    adminDashboard.setVisible(true);
                } else {
                    // Regular user login
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mpr", "root", "shubham1332");
                        String query = "SELECT * FROM USERS WHERE EMAIL = ? AND PASS_WORD = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, email);
                        ps.setString(2, password);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            // Login successful
                            JOptionPane.showMessageDialog(null, "Successfully logged in!", "Login", JOptionPane.INFORMATION_MESSAGE);

                            // Open Quiz application
                            dispose();
                            QuizAppInterface quizapp = new QuizAppInterface(); // Assuming this is your Quiz app class
                            quizapp.setVisible(true);
                        } else {
                            // Invalid credentials
                            JOptionPane.showMessageDialog(null, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        rs.close();
                        ps.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        mainPanel.add(continueButton);

        // Add scroll pane to main panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        setContentPane(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TechSageSignIn signInForm = new TechSageSignIn();
            signInForm.setVisible(true);
        });
    }
}