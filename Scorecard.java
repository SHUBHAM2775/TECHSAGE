import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scorecard extends JFrame {
    // Variables for score tracking
    private int correctAnswers;
    private int incorrectAnswers;
    private int totalQuestions;

    // Constructor to accept score details
    public Scorecard(int correctAnswers, int incorrectAnswers, int totalQuestions) {
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.totalQuestions = totalQuestions;

        // Set frame properties
        setTitle("Scorecard");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Left panel with options (drop-down menus)
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(220, getHeight()));
        leftPanel.setBackground(Color.decode("#1a1a1a"));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Blue A icon at the top of the sidebar
        JLabel blueAIconLabel = new JLabel();
        ImageIcon blueAIcon = new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\BlueA.png");
        Image scaledBlueAIcon = blueAIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        blueAIconLabel.setIcon(new ImageIcon(scaledBlueAIcon));
        blueAIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueAIconLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Top spacing for Blue A icon

    
        // Drop-down menu for choosing subjects
        String[] subjects = {"DSA" , "DBMS"};
        JComboBox<String> subjectsComboBox = createComboBox(subjects, "Choose Subject");
        subjectsComboBox.setEnabled(true); // Make the combo box unselectable

        // Drop-down menu for choosing difficulty level
        String[] difficultyLevels = {"Easy", "Moderate", "Tough"};
        JComboBox<String> difficultyComboBox = createComboBox(difficultyLevels, "Choose Difficulty");
        difficultyComboBox.setEnabled(true); // Make the combo box unselectable

        // Drop-down menu for choosing modules
        String[] modules = {"Module 1", "Module 2", "Module 3"};
        JComboBox<String> modulesComboBox = createComboBox(modules, "Choose Module");
        modulesComboBox.setEnabled(true); // Make the combo box unselectable

        // Logout button
        JButton logoutButton = createButton("Log Out", 18f);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> openHomePage());

        // Add components to left panel
        leftPanel.add(blueAIconLabel); // Add Blue A icon at the top
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        leftPanel.add(subjectsComboBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(difficultyComboBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(modulesComboBox);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoutButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Center panel for the scorecard details using GridBagLayout for centering
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Adds space between components

        // Title label
        JLabel scorecardLabel = new JLabel("Scorecard", JLabel.CENTER);
        scorecardLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        scorecardLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(scorecardLabel, gbc);

        // Total questions label - Dynamic based on the input
        JLabel totalQuestionsLabel = new JLabel("Total no of questions: " + totalQuestions, JLabel.CENTER);
        totalQuestionsLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        totalQuestionsLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        centerPanel.add(totalQuestionsLabel, gbc);

        // Correct label - Dynamic based on the input
        JLabel correctLabel = new JLabel("Correct: " + correctAnswers, JLabel.CENTER);
        correctLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        correctLabel.setForeground(Color.GREEN);
        gbc.gridy = 2;
        centerPanel.add(correctLabel, gbc);

        // Incorrect label - Dynamic based on the input
        JLabel incorrectLabel = new JLabel("Incorrect: " + incorrectAnswers, JLabel.CENTER);
        incorrectLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        incorrectLabel.setForeground(Color.RED);
        gbc.gridy = 3;
        centerPanel.add(incorrectLabel, gbc);

        // Back to Home button aligned at the bottom center
        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        backButton.setBackground(new Color(73, 0, 204)); // Purple color
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(30, 0, 0, 0); // Extra top spacing
        centerPanel.add(backButton, gbc);

        backButton.addActionListener(e -> openSelection());

        // Panel for positioning the TechSage logo at the top-right corner
        JPanel topRightPanel = new JPanel(new BorderLayout());
        topRightPanel.setBackground(Color.BLACK);
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg");
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);  // Logo size
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogoImage));
        logoLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Add the TechSage logo to the top-right panel
        topRightPanel.add(logoLabel, BorderLayout.EAST);

        // Add components to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);  // Left sidebar
        mainPanel.add(centerPanel, BorderLayout.CENTER);  // Center scorecard content
        mainPanel.add(topRightPanel, BorderLayout.NORTH);  // TechSage logo at the top-right corner

        // Add main panel to the frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    public Scorecard() {
        //TODO Auto-generated constructor stub
    }

    // Utility method to create a styled button
    private JButton createButton(String text, float fontSize) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(fontSize)); // Set font size
        button.setBackground(new Color(73, 0, 204)); // Purple color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Utility method to create a styled combo box
    private JComboBox<String> createComboBox(String[] options, String title) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setMaximumSize(new Dimension(200, 50));
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        comboBox.setBackground(Color.BLACK); // Set the background color to black
        comboBox.setForeground(Color.BLACK); // Set the text color to black
        comboBox.setEnabled(false); // Disable the combo box
        return comboBox;
    }

    public void openSelection() {
        // Close current window
        this.dispose();
        // Open selection frame
        QuizAppInterface select = new QuizAppInterface();
        select.setVisible(true);
    }

    public void openHomePage() {
        // Close current window
        this.dispose();
        // Open main frame
        TechSageMain mainPage = new TechSageMain();
        mainPage.setVisible(true);
    }

    public static void main(String[] args) {
        // Example call with 8 correct answers, 2 incorrect answers, and total 10 questions
        SwingUtilities.invokeLater(() -> new Scorecard(8, 2, 10));
    }
}
