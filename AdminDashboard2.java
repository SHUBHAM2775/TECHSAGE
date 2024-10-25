import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class AdminDashboard2 extends JFrame {

    public AdminDashboard2() {
        // Set frame properties
        setTitle("Make Quiz");
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

        // Settings button
        JButton settingsButton = createButton("View History", 18f);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(200, 50));
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openHistory();
            }
        });

        // View Profiles button
        JButton viewProfilesButton = createButton("View Profiles", 18f);
        viewProfilesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewProfilesButton.setMaximumSize(new Dimension(200, 50));

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
        leftPanel.add(settingsButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(viewProfilesButton);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoutButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Right panel with GridBagLayout for flexibility
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        // Add a label "Make Quiz" at the top
        JLabel makeQuizLabel = new JLabel("Make Quiz", JLabel.CENTER);
        makeQuizLabel.setForeground(Color.WHITE);
        makeQuizLabel.setFont(new Font("SansSerif", Font.BOLD, 32));

        // Add the label to the right panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(makeQuizLabel, gbc);

        // Add dropdowns for Subject and Module
        JComboBox<String> subjectDropdown = new JComboBox<>(new String[] { "Subject", "DSA", "DBMS"});
        subjectDropdown.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        rightPanel.add(subjectDropdown, gbc);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Vertical space after Subject dropdown

        JComboBox<String> moduleDropdown = new JComboBox<>(
                new String[] { "Module 1", "Module 2", "Module 3", "Module 4", "Module 5", "Module 6" });
        moduleDropdown.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 2;
        rightPanel.add(moduleDropdown, gbc);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Vertical space after Module dropdown

        // Add a text field for the question
        JTextField questionField = new JTextField("Question");
        questionField.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        rightPanel.add(questionField, gbc);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Vertical space after Question field

        // Add options as radio buttons with text fields
        JRadioButton option1RadioButton = new JRadioButton("Option 1");
        option1RadioButton.setBackground(Color.BLACK);
        option1RadioButton.setForeground(Color.WHITE);
        JTextField option1TextField = new JTextField("Enter Option 1 Text");

        JRadioButton option2RadioButton = new JRadioButton("Option 2");
        option2RadioButton.setBackground(Color.BLACK);
        option2RadioButton.setForeground(Color.WHITE);
        JTextField option2TextField = new JTextField("Enter Option 2 Text");

        JRadioButton option3RadioButton = new JRadioButton("Option 3");
        option3RadioButton.setBackground(Color.BLACK);
        option3RadioButton.setForeground(Color.WHITE);
        JTextField option3TextField = new JTextField("Enter Option 3 Text");

        JRadioButton option4RadioButton = new JRadioButton("Option 4");
        option4RadioButton.setBackground(Color.BLACK);
        option4RadioButton.setForeground(Color.WHITE);
        JTextField option4TextField = new JTextField("Enter Option 4 Text");

        // Create a panel for the options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(Color.BLACK);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        // Create a ButtonGroup to ensure only one radio button can be selected
        ButtonGroup buttonGroup = new ButtonGroup();

        // Add the radio buttons and their text fields to the options panel and button group
        optionsPanel.add(option1RadioButton);
        option1TextField.setPreferredSize(new Dimension(200, 30)); // Set preferred size for option1TextField
        optionsPanel.add(option1TextField);
        buttonGroup.add(option1RadioButton);

        optionsPanel.add(option2RadioButton);
        optionsPanel.add(option2TextField);
        buttonGroup.add(option2RadioButton);

        optionsPanel.add(option3RadioButton);
        optionsPanel.add(option3TextField);
        buttonGroup.add(option3RadioButton);

        optionsPanel.add(option4RadioButton);
        optionsPanel.add(option4TextField);
        buttonGroup.add(option4RadioButton);

        // Add optionsPanel to the right panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        rightPanel.add(optionsPanel, gbc);

        // Add "Add Question" button
        JButton addQuestionButton = createButton("Add Question", 20f);
        addQuestionButton.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 0; // Place the button in the first column
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        rightPanel.add(addQuestionButton, gbc);

        // Add space between the buttons
        gbc.gridy = 7; // Move to the next row
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc); // Add vertical space

        // Add "Add Bulk Question" button just below the "Add Question" button
        JButton addBulkQuestionButton = createButton("Add Bulk Qs", 20f);
        addBulkQuestionButton.setPreferredSize(new Dimension(250, 50));
        gbc.gridy = 8; // Place the button in the row below the space
        gbc.anchor = GridBagConstraints.NORTHEAST; // Adjust anchor to place it below the "Add Question" button
        rightPanel.add(addBulkQuestionButton, gbc);

        addBulkQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BulkQuestionUpload bq = new BulkQuestionUpload();
                bq.addBulkQuestion(01, "02");
            }
        });

        ImageIcon logoIcon = new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg");
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);

        JLabel logoLabel = new JLabel(scaledLogoIcon);

        // Add logo to NORTH, content to CENTER
        gbc.gridx = 10; // Align logo to the right
        gbc.gridy = 0; // Same row as the title
        gbc.anchor = GridBagConstraints.NORTHEAST; // Align to northeast
        rightPanel.add(logoLabel, gbc);

        // Add panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add main panel to the frame
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

    // Dummy method for navigation to Home Page (log out)
    private void openHomePage() {
        this.dispose();
        // Open Main frame
        TechSageMain mainPage = new TechSageMain();
        mainPage.setVisible(true);
        // Add your actual navigation code here
    }
    public void openHistory() {
        this.dispose();
        ExamHistory history = new ExamHistory();
        history.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard2().setVisible(true);
        });
    }
}
