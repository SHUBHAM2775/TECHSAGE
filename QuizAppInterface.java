import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizAppInterface extends JFrame {
    private String[][] quizData;

    public QuizAppInterface() {
        setTitle("TechSage Quiz Interface");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout for main frame
        setLayout(new BorderLayout());

        // Left sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBackground(Color.decode("#1a1a1a"));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        sidebarPanel.add(Box.createVerticalStrut(25)); // Spacer
        // Custom JLabel for user icon
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\BlueA.png");
        Image iconImage = originalIcon.getImage();
        Image scaledIconImage = iconImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledIconImage);

        JLabel iconLabel = new JLabel(scaledIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebarPanel.add(iconLabel);

        sidebarPanel.add(Box.createVerticalStrut(35)); // Spacer

        // Navigation buttons with increased size
        JButton settingsButton = new JButton("View Exam History");
        settingsButton.setPreferredSize(new Dimension(180, 60)); // Increased size
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBackground(Color.decode("#333333"));
        settingsButton.setFocusPainted(false);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        settingsButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            viewHistory();
        }
    });

        JButton resultsButton = new JButton("Results");
        resultsButton.setPreferredSize(new Dimension(180, 60)); // Increased size
        resultsButton.setForeground(Color.WHITE);
        resultsButton.setBackground(Color.decode("#333333"));
        resultsButton.setFocusPainted(false);
        resultsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Results button clicked"); // Debug line
                openResult();
            }
        });
            

        sidebarPanel.add(Box.createVerticalStrut(25)); // Spacer
        sidebarPanel.add(settingsButton);
        sidebarPanel.add(Box.createVerticalStrut(25)); // Spacer
        sidebarPanel.add(resultsButton);

        // Add vertical glue to push the logout button to the bottom
        sidebarPanel.add(Box.createVerticalGlue());

        // Logout button
        JButton logoutButton = new JButton("LogOut");
        logoutButton.setPreferredSize(new Dimension(180, 50)); // Increased size
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(Color.decode("#5A00FF"));
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sidebarPanel.add(logoutButton);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHomePage();
            }
        });

        // Main content panel
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.BLACK);
        mainContentPanel.setLayout(null);

        JLabel logoLabel = new JLabel(new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg"));
        logoLabel.setBounds(425, 80, 475, 350); // Adjust these values
        mainContentPanel.add(logoLabel);

        // "Make your Quiz" label
        JLabel makeYourQuizLabel = new JLabel("Make your Quiz");
        makeYourQuizLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        makeYourQuizLabel.setForeground(Color.WHITE);
        makeYourQuizLabel.setBounds(575, 450, 200, 30);
        mainContentPanel.add(makeYourQuizLabel);

        // Dropdowns and Continue button
        String[] subjects = { "Subject", "DSA", "DBMS"};
        String[] levels = { "Level", "Easy", "Medium", "Hard" };
        String[] modules = {"Module 1", "Module 2", "Module 3", "Module 4"};

        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);
        subjectComboBox.setBounds(550, 500, 225, 40);

        JComboBox<String> levelComboBox = new JComboBox<>(levels);
        levelComboBox.setBounds(550, 550, 225, 40);

        JComboBox<String> moduleComboBox = new JComboBox<>(modules);
        moduleComboBox.setBounds(550, 600, 225, 40);

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(550, 650, 225, 50);
        continueButton.setBackground(Color.decode("#5A00FF"));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        mainContentPanel.add(subjectComboBox);
        mainContentPanel.add(levelComboBox);
        mainContentPanel.add(moduleComboBox);
        mainContentPanel.add(continueButton);

        // Add panels to the frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        // Action for the "Continue" button
        // Inside QuizAppInterface class
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subject = (String) subjectComboBox.getSelectedItem();
                String level = (String) levelComboBox.getSelectedItem();
                String module = (String) moduleComboBox.getSelectedItem();

                // Pass subject, level, and module to the new QuizPage
                new QuizPage(subject, level, module).setVisible(true);

                // Close the selection window
                dispose();
            }
        });

    }

    public void loadQuizPanel(String subject, String level, String module) {
        // Sample questions for this example
        if (subject.equals("DBMS") && level.equals("Easy") && module.equals("Mod 2: ER")) {
            quizData = new String[][] {
                    { "Q1] Which of the following are constraints on ER?", "Cardinality", "Disjoint", "Entity",
                            "Relationship" },
                    { "Q2] What is an ER Model?", "Diagram", "Representation", "Data", "Entity" },
                    // More questions...
            };
        }
        setupQuizLayout();
        updateQuizContent();
    }

    private void setupQuizLayout() {
        // Set up quiz layout (question, options, buttons, etc.)
        // ...
    }

    private void updateQuizContent() {
        // Update question and options based on the current index
        // ...
    }
    public void openResult(){
        this.dispose();
        Scorecard selection = new Scorecard();
        selection.setVisible(true);
    }

    public void viewHistory(){
        this.dispose();
        ExamHistory history = new ExamHistory();
        history.setVisible(true);
    }

    public void openHomePage() {
        // Close current window
        this.dispose();
        // Open Main frame
        TechSageMain mainPage = new TechSageMain();
        mainPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizAppInterface appInterface = new QuizAppInterface();
            appInterface.setVisible(true);
        });
    }
}
