import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechSageSignUp extends JFrame {

    public TechSageSignUp() {
        setTitle("TechSage - Sign Up");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(null);

        // Logo Image (Replace with actual logo path)
        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg"));
        logoLabel.setBounds(550, 50, 500, 350); // Adjust size and location as needed
        mainPanel.add(logoLabel);

        // Welcome Text
        JLabel welcomeLabel = new JLabel("Nice to see you!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(625, 400, 350, 30);
        mainPanel.add(welcomeLabel);

        // User Name Field
        JTextField userNameField = new JTextField("Enter your user name");
        userNameField.setBounds(650, 450, 300, 40);
        mainPanel.add(userNameField);

        // Email Field
        JTextField emailField = new JTextField("Enter your email address");
        emailField.setBounds(650, 500, 300, 40);
        mainPanel.add(emailField);

        // Phone Number Field
        JTextField phoneField = new JTextField("Enter your phone number");
        phoneField.setBounds(650, 550, 300, 40);
        mainPanel.add(phoneField);

        // Password Field
        JPasswordField passwordField = new JPasswordField("Enter Password");
        passwordField.setBounds(650, 600, 300, 40);
        mainPanel.add(passwordField);

        // Confirm Password Field
        JPasswordField confirmPasswordField = new JPasswordField("Cnfrm Password");
        confirmPasswordField.setBounds(650, 650, 300, 40);
        mainPanel.add(confirmPasswordField);

        // Terms & Conditions Checkbox
        JCheckBox termsCheckBox = new JCheckBox("I agree with Terms & Conditions");
        termsCheckBox.setForeground(Color.WHITE);
        termsCheckBox.setBackground(Color.BLACK);
        termsCheckBox.setBounds(650, 700, 300, 30);
        mainPanel.add(termsCheckBox);

        // Continue Button
        JButton continueButton = new JButton("Register");
        continueButton.setBackground(new Color(128, 0, 255)); // Purple color
        continueButton.setForeground(Color.WHITE);
        continueButton.setBounds(650, 750, 140, 40); // Adjusted width to make space for the new button
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match! Please re-enter your password.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Clear password fields
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                } else if (!termsCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(null, "You must agree to the Terms & Conditions.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert user data into the database
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mpr", "root", "shubham1332");

                        // Fetch the current maximum USERID
                        String fetchMaxIDQuery = "SELECT USERID FROM USERS ORDER BY USERID DESC LIMIT 1";
                        PreparedStatement fetchMaxIDStmt = conn.prepareStatement(fetchMaxIDQuery);
                        ResultSet rs = fetchMaxIDStmt.executeQuery();

                        String newUserID;
                        if (rs.next()) {
                            // Get the last USERID and increment it
                            String lastID = rs.getString("USERID");
                            int nextID = Integer.parseInt(lastID) + 1;
                            newUserID = String.format("%04d", nextID); // Format as 4-digit ID with leading zeros
                        } else {
                            // No users exist, start with 0001
                            newUserID = "0001";
                        }

                        // Prepare the INSERT statement
                        String insertQuery = "INSERT INTO USERS (USERID, PHONE_NO, USERNAME, PASS_WORD, EMAIL) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

                        // Set the values
                        preparedStatement.setString(1, newUserID);
                        preparedStatement.setString(2, phone);
                        preparedStatement.setString(3, userName);
                        preparedStatement.setString(4, password);
                        preparedStatement.setString(5, email);

                        // Execute the query
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Sign Up successful! Your User ID is: " + newUserID);
                            dispose(); // Close the current window
                            TechSageSignIn signInForm = new TechSageSignIn();
                            signInForm.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Sign Up failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Close the connection
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        mainPanel.add(continueButton);

        // Back to Home Button
        JButton backButton = new JButton("Go to Login");
        backButton.setBackground(new Color(128, 0, 255)); // Purple color
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(810, 750, 140, 40); // Positioned next to the Continue button
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Assuming you have a HomePage class to navigate to
                TechSageSignIn homePage = new TechSageSignIn();
                homePage.setVisible(true);
            }
        });
        mainPanel.add(backButton);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TechSageSignUp signUpForm = new TechSageSignUp();
            signUpForm.setVisible(true);
        });
    }
}