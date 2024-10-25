import javax.swing.*;

import com.mysql.cj.xdevapi.Result;

//import com.mysql.cj.xdevapi.Statement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ModifySubject extends JFrame {

    Connection conn;
    Statement stmt;

    public ModifySubject() {
        // Set frame properties
        setTitle("Modify Subject");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Left panel with options
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(220, getHeight()));
        leftPanel.setBackground(Color.decode("#1a1a1a"));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Icon (Settings)
        ImageIcon originalIcon = new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\BlueA.png");
        Image iconImage = originalIcon.getImage();
        Image scaledIconImage = iconImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledIconImage);

        JLabel iconLabel = new JLabel(scaledIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // View History button
        JButton viewHistoryButton = createButton("View History", 18f);
        viewHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewHistoryButton.setMaximumSize(new Dimension(200, 50));
        viewHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openHistory();
            }
        });

        // View Profiles button
        JButton homebutton2 = createButton("Back to Home", 18f);
        homebutton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        homebutton2.setMaximumSize(new Dimension(200, 50));
        homebutton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminhome();
            }
        });


        // Logout button
        JButton logoutButton = createButton("Log Out", 18f);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBackground(new Color(73, 0, 204)); // Purple color
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setMaximumSize(new Dimension(200, 50));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHomePage();
            }
        });

        // Add components to left panel
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        leftPanel.add(viewHistoryButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(homebutton2);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoutButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        //Getting Database Connection

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mpr", "root", "shubham1332");
            stmt = (Statement) conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Right panel with GridBagLayout for flexibility
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        // Add a label "Modify Subject" at the top
        JLabel modifySubjectLabel = new JLabel("Modify Subject", JLabel.CENTER);
        modifySubjectLabel.setForeground(Color.WHITE);
        modifySubjectLabel.setFont(new Font("SansSerif", Font.BOLD, 32));

        // Add the label to the right panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(modifySubjectLabel, gbc);

        JTextField subjectID = createTextField("Enter Subject ID");
        subjectID.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 1;
        rightPanel.add(subjectID, gbc);

        JTextField subjectName = createTextField("Enter Subject Name");
        subjectName.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 2;
        rightPanel.add(subjectName, gbc);

        JTextField moduleField = createTextField("Enter Number of Modules");
        moduleField.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 3;
        rightPanel.add(moduleField, gbc);

        JTextField Semester = createTextField("Enter Your Semester");
        Semester.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 4;
        rightPanel.add(Semester, gbc);

        gbc.gridy = 5;
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc); // Add vertical space

        // Add "Save Changes" button
        JButton addsubject = createButton("Add Subject", 20f);
        addsubject.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        rightPanel.add(addsubject, gbc);

        addsubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add subject to the database
                int subjectIDText = Integer.parseInt(subjectID.getText());
                String subjectNameText = subjectName.getText();
                int moduleFieldText = Integer.parseInt(moduleField.getText());
                int SemesterText = Integer.parseInt(Semester.getText());

                // Add subject to the database
                // Database.addSubject(subjectIDText, subjectNameText, moduleFieldText, SemesterText);
                try{
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM SUBJECT");
                    if(rs.next())
                        subjectIDText = rs.getInt(1) + 1;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                //String query = "INSERT INTO SUBJECT (SUB_ID, SUB_NAME, NO_MODULE, SEMESTER) VALUES (?, ?, ?, ?)";
                String query = " INSERT INTO SUBJECT (SUB_ID, SUB_NAME, NO_MODULE, SEMESTER) VALUES (" + subjectIDText + ",'" + subjectNameText + "'," + moduleFieldText + "," +SemesterText + ")";
                try {
                    ((java.sql.Statement) stmt).executeUpdate(query);
                    //((Connection) stmt).close();
                    conn.close();
                    System.out.println("Subject added successfully!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        // Add space between the buttons
        gbc.gridy = 7;
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc); // Add vertical space

        // Add "Cancel" button just below the "Save Changes" button
        JButton removeSubject = createButton("Remove Subject", 20f);
        removeSubject.setPreferredSize(new Dimension(250, 50));
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.NORTHEAST; // Adjust anchor to place it below the "Save Changes" button
        rightPanel.add(removeSubject, gbc);
        removeSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add subject to the database
                int subjectIDText = Integer.parseInt(subjectID.getText());

                //String query = "INSERT INTO SUBJECT (SUB_ID, SUB_NAME, NO_MODULE, SEMESTER) VALUES (?, ?, ?, ?)";
                String query = " DELETE FROM SUBJECT WHERE SUB_ID = " + subjectIDText;
                try {
                    ((java.sql.Statement) stmt).executeUpdate(query);
                    //((Connection) stmt).close();
                    conn.close();
                    System.out.println("Subject deleted successfully!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });



        ImageIcon logoIcon = new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg");
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);

        JLabel logoLabel = new JLabel(scaledLogoIcon);

        // Add logo to NORTH, content to CENTER
        gbc.gridx = 10; 
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.NORTHEAST; 
        rightPanel.add(logoLabel, gbc);

        // Add panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Utility method to create a styled button
    private JButton createButton(String text, float fontSize) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(fontSize));
        button.setFocusPainted(false);
        button.setBackground(new Color(73, 0, 204)); // Purple background
        button.setForeground(Color.WHITE); // White text
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Utility method to create a text field with click listener
private JTextField createTextField(String placeholder) {
    JTextField textField = new JTextField(placeholder);
    textField.setPreferredSize(new Dimension(200, 40));
    textField.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            textField.setText(""); // Clear text on click
        }
    });
    return textField;
}


        private void openHomePage() {
        this.dispose();
        TechSageMain quizAppInterface = new TechSageMain();
        quizAppInterface.setVisible(true);
    }

    
    private void openHistory() {
        this.dispose();
        ExamHistory history = new ExamHistory();
        history.setVisible(true);
    }

    public void adminhome() {
        this.dispose();
        AdminDashboard home = new AdminDashboard();
        home.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModifySubject modifySubject = new ModifySubject();
            modifySubject.setVisible(true);
        });
    }
}