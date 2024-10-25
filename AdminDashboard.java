import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        // Set frame properties
        setTitle("Admin Dashboard");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Left sidebar panel with options (adjusted to start from the top)
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
        JButton settingsButton = createButton("View Exam History", 18f);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(200, 50));
        settingsButton.addActionListener(e -> viewHistory());

        // View Profiles button
        JButton homebutton2 = createButton("Back to Home", 18f);
        homebutton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        homebutton2.setMaximumSize(new Dimension(200, 50));
        homebutton2.addActionListener(e -> adminhome());

        // Logout button
        JButton logoutButton = createButton("Log Out", 18f);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBackground(new Color(73, 0, 204));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setMaximumSize(new Dimension(200, 50));
        logoutButton.addActionListener(e -> openHomePage());

        // Add components to left panel (starting from the top)
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(settingsButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(homebutton2);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoutButton);

        // Right panel with GridBagLayout for flexibility
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 30, 15, 30);

        // Welcome label (centered)
        JLabel welcomeLabel = new JLabel("Welcome Admin!", JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(welcomeLabel, gbc);

        // Buttons for the admin panel
        String[] buttonLabels = {"Modify Subject", "Modify Question"};
        int row = 1;
        for (String label : buttonLabels) {
            JButton button = createButton(label, 20f);
            button.setPreferredSize(new Dimension(250, 70));
            gbc.gridx = (row - 1) % 2;
            gbc.gridy = (row - 1) / 2 + 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.CENTER;

            // Add action listener for "Add Subject" button
            if (label.equals("Modify Question")) {
                button.addActionListener(e -> openAdminDashboard2());
            }
            if (label.equals("Modify Subject")) {
                button.addActionListener(e -> modifySubject());
            }

            rightPanel.add(button, gbc);
            row++;
        }

        // Panel for positioning the logo at the top-right corner
        JPanel topRightPanel = new JPanel(new BorderLayout());
        topRightPanel.setBackground(Color.BLACK);
        ImageIcon logoIcon = new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg"); 
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);  // Increased size to 250x250
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Add the logo to topRightPanel
        topRightPanel.add(logoLabel, BorderLayout.EAST);
        mainPanel.add(topRightPanel, BorderLayout.NORTH);  // Logo at top-right corner
        
        // Add panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);  // Left sidebar to the west
        mainPanel.add(rightPanel, BorderLayout.CENTER);  // Right side content

        // Add main panel to the frame
        add(mainPanel);
    }

    // Utility method to create a styled button
    private JButton createButton(String text, float fontSize) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(fontSize));
        button.setBackground(new Color(73, 0, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public void openHomePage() {
        this.dispose();
        TechSageMain mainPage = new TechSageMain();
        mainPage.setVisible(true);
    }

    public void viewHistory()
    {
        this.dispose();
        ExamHistory history = new ExamHistory();
        history.setVisible(true);
    }

    public void openAdminDashboard2() {
        this.dispose(); // Close the current AdminDashboard frame
        AdminDashboard2 dashboard2 = new AdminDashboard2();
        dashboard2.setVisible(true); // Open the new AdminDashboard2 frame
    }

    public void modifySubject() {
        this.dispose(); // Close the current AdminDashboard frame
        ModifySubject modifySubject = new ModifySubject();
        modifySubject.setVisible(true); // Open the new ModifySubject frame
    }

    public void adminhome() {
        this.dispose();
        AdminDashboard home = new AdminDashboard();
        home.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard frame = new AdminDashboard();
            frame.setVisible(true);
        });
    }
}