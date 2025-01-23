import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ExamHistory extends JFrame {

    public ExamHistory() {
        // Set frame properties
        setTitle("Admin Dashboard - Exam History");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout and black background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Left sidebar panel with options (adjusted to start from the top)
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(220, getHeight()));
        leftPanel.setBackground(Color.decode("#1a1a1a"));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Logo (A logo at the top)
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\BlueA.png");
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // History button
        JButton historyButton = createButton("Result", 18f);
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.setMaximumSize(new Dimension(200, 50));
        historyButton.addActionListener(e -> openResult());

        // View Profiles button
        JButton viewProfilesButton = createButton("Home", 18f);
        viewProfilesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewProfilesButton.setMaximumSize(new Dimension(200, 50));
        viewProfilesButton.addActionListener(e -> openQuizAppInterface());

        // Logout button
        JButton logoutButton = createButton("Log Out", 18f);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(200, 50)); // Set the same size as the other buttons
        logoutButton.addActionListener(e -> openHomePage());

        // Add components to left panel
        leftPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createVerticalStrut(20)); // Add space between components
        leftPanel.add(historyButton);
        leftPanel.add(Box.createVerticalStrut(20)); // Add space between components
        leftPanel.add(viewProfilesButton);
        leftPanel.add(Box.createVerticalStrut(20)); // Add space between components
        leftPanel.add(logoutButton);

        // Add left panel to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel with GridBagLayout for flexibility
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Table headings
        String[] columnNames = {"Past Tests", "Marks", "Subjects"};
        Object[][] data = fetchExamHistoryData(); // Fetch data from the database

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 24));
        table.getTableHeader().setBackground(Color.GRAY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(Color.decode("#333333"));
        table.setForeground(Color.WHITE);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        scrollPane.getViewport().setBackground(Color.BLACK);

        // Add the scroll pane to the right panel
        rightPanel.add(scrollPane, gbc);

        // Add panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);
    }

    // Method to fetch exam history data from the database
    private Object[][] fetchExamHistoryData() {
        Object[][] data = {};

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mpr", "root", "shubham1332");
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT TESTID, CORRECT, ATTEMPTS, SUB_ID FROM EXAM_HISTORY")) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            data = new Object[rowCount][3]; // Ensure we have three columns

            int rowIndex = 0;
            while (rs.next()) {
                String testID = rs.getString("TESTID");
                int correct = rs.getInt("CORRECT");
                int attempts = rs.getInt("ATTEMPTS");
                String marks = correct + "/" + attempts;

                // Get the subject name based on SUB_ID
                String subject="DSA";
                // int subId = rs.getInt("SUB_ID");
                // if (subId == 1) {
                //     subject = "DSA";
                // } else if (subId == 2) {
                //     subject = "DBMS";
                // } 
                // else {
                //     subject = "Unknown"; // Handle other cases
                // }

                data[rowIndex][0] = testID; // Past Tests column
                data[rowIndex][1] = marks;   // Marks column
                data[rowIndex][2] = subject;  // Updated Subjects column

                rowIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }  

    // Utility method to create a styled button
    private JButton createButton(String text, float fontSize) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#333333"));
        button.setFocusPainted(false);
        return button;
    }

    public void openHomePage() {
        this.dispose();
        TechSageMain mainPage = new TechSageMain();
        mainPage.setVisible(true);
    }

    public void openResult() {
        this.dispose();
        Scorecard selection = new Scorecard();
        selection.setVisible(true);
    }

    public void openQuizAppInterface() {
        this.dispose();
        QuizAppInterface quizAppInterface = new QuizAppInterface();
        quizAppInterface.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExamHistory frame = new ExamHistory();
            frame.setVisible(true);
        });
    }
}
