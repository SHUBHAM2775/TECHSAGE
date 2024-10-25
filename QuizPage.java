import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuizPage extends JFrame {
    private String[][] quizData;

    private int currentQuestionIndex = 0;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;

    // New fields to track user answers
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private String[] correctAnswersArray; // Store correct answers
    private String[] userAnswers; // Store user's selected answers

    public QuizPage(String subject, String level, String module) {
        setTitle("Quiz - " + subject + " (" + module + ", " + level + ")");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background color
        getContentPane().setBackground(Color.BLACK);

        // Set quiz data based on the subject, level, and module
        loadQuizData(subject, level, module);

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

        // Navigation buttons
        JButton settingsButton = new JButton("View History");
        styleButton(settingsButton);
        sidebarPanel.add(settingsButton);
        sidebarPanel.add(Box.createVerticalStrut(25)); // Spacer
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewHistory();
            }
        });

        JButton resultsButton = new JButton("Results");
        styleButton(resultsButton);
        sidebarPanel.add(resultsButton);
        sidebarPanel.add(Box.createVerticalStrut(25)); // Spacer
        resultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openResult();
            }
        });

        // Logout button
        JButton logoutButton = new JButton("LogOut");
        styleButton(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openHomePage();
            }
        });
        sidebarPanel.add(logoutButton);
        
        sidebarPanel.add(Box.createVerticalGlue());

        // Main content panel
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.BLACK);
        mainContentPanel.setLayout(null);
        
        JLabel logoLabel = new JLabel(new ImageIcon(
                "C:\\Users\\SIDDHI SUSHIR\\JAVA_MPR\\TECHSAGE-MPR-FINAL\\TechSage.Logo.jpg"));
        logoLabel.setBounds(1350, 10, 275, 250);
        mainContentPanel.add(logoLabel);

        questionLabel = new JLabel();
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBounds(300, 250, 1000, 40);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        mainContentPanel.add(questionLabel);

        // Options area
        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(400, 320 + (i * 50), 1000, 40);
            options[i].setForeground(Color.WHITE);
            options[i].setBackground(Color.BLACK);
            options[i].setFont(new Font("Arial", Font.PLAIN, 20));
            optionGroup.add(options[i]);
            mainContentPanel.add(options[i]);
        }

        // Load first question
        updateQuizContent();

        // Navigation buttons
        JButton prevButton = new JButton("Prev Ques");
        prevButton.setBounds(500, 600, 150, 50);
        prevButton.setBackground(Color.decode("#5A00FF"));
        prevButton.setForeground(Color.WHITE);
        prevButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateQuizContent();
            }
        });
        mainContentPanel.add(prevButton);

        JButton nextButton = new JButton("Next Ques");
        nextButton.setBounds(700, 600, 150, 50);
        nextButton.setBackground(Color.decode("#5A00FF"));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < Math.min(quizData.length - 1, 9)) {
                // Save user's selected answer
                saveUserAnswer();
                currentQuestionIndex++;
                updateQuizContent();
            }
            // Show only "Prev Ques" and "End Quiz" buttons on the last question
            if (currentQuestionIndex == Math.min(quizData.length - 1, 9)) {
                nextButton.setEnabled(false); // Disable next button on the last question
            }
        });
        mainContentPanel.add(nextButton);

        JButton endButton = new JButton("End Quiz");
        endButton.setBounds(900, 600, 150, 50);
        endButton.setBackground(Color.decode("#5A00FF"));
        endButton.setForeground(Color.WHITE);
        mainContentPanel.add(endButton);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserAnswer(); // Save the last selected answer
                calculateScore(); // Calculate score before opening result
                savedata();
                openResult();
            }
        });

        // Add panels to the frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(180, 60));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#333333"));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void updateQuizContent() {
        // Update the question and options
        questionLabel.setText(quizData[currentQuestionIndex][0]);
        for (int i = 0; i < options.length; i++) {
            options[i].setText(quizData[currentQuestionIndex][i + 1]);
            options[i].setSelected(userAnswers[currentQuestionIndex] != null && 
             userAnswers[currentQuestionIndex].equals(options[i].getText()));
        }
        optionGroup.clearSelection(); // Clear previous selection
    }

    private void saveUserAnswer() {
        String selectedOption = null;
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                selectedOption = option.getText();
                break;
            }
        }
        userAnswers[currentQuestionIndex] = selectedOption; // Save the selected option
    }

    private void calculateScore() {
        // Check user answers and calculate correct and incorrect answers
        for (int i = 0; i < quizData.length && i < userAnswers.length; i++) {
            if (userAnswers[i] != null) {
                // Compare with the correct answer
                if (userAnswers[i].equals(correctAnswersArray[i])) {
                    correctAnswers++;
                } else {
                    incorrectAnswers++;
                }
            }
        }
    }

    public void savedata() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mpr", "root", "shubham1332");
            Statement st = conn.createStatement();
    
            // Query to get the latest HISTORY_ID
            String getLastIDQuery = "SELECT MAX(HISTORY_ID) FROM EXAM_HISTORY";
            ResultSet rs = st.executeQuery(getLastIDQuery);
    
            int newHistoryID = 1; // Default value if no records exist
            if (rs.next() && rs.getInt(1) > 0) {
                newHistoryID = rs.getInt(1) + 1; // Increment last HISTORY_ID by 1
            }
    
            // Query to get the latest TESTID
            String getLastTestIDQuery = "SELECT TESTID FROM EXAM_HISTORY ORDER BY HISTORY_ID DESC LIMIT 1";
            rs = st.executeQuery(getLastTestIDQuery);
    
            String newTestID = "TEST001"; // Default value if no records exist
            if (rs.next()) {
                String lastTestID = rs.getString(1); // Get the last TESTID, e.g., "TEST001"
                int testNumber = Integer.parseInt(lastTestID.substring(4)) + 1; // Extract number, increment by 1
                newTestID = String.format("TEST%03d", testNumber); // Format to "TEST002", "TEST003", etc.
            }
    
            // Calculate attempts as the sum of correct and incorrect answers
            int attempts = 10;
    
            // Insert quiz results into EXAM_HISTORY
            String query = "INSERT INTO EXAM_HISTORY (HISTORY_ID, TESTID, MOD_ID, LEVEL, ATTEMPTS, CORRECT, WRONG) VALUES (" 
                           + newHistoryID + ", " 
                           + "'" + newTestID + "', " // Insert the incremented TESTID
                           + "1, " // Replace with appropriate MOD_ID if available
                           + "'Level 1', " 
                           + attempts + ", " 
                           + correctAnswers + ", " 
                           + incorrectAnswers + ")";
    
            st.executeUpdate(query);
            System.out.println("Data saved with TESTID: " + newTestID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewHistory() {
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

    public void openResult() {
        this.dispose();
        // Pass the score to Scorecard
        Scorecard selection = new Scorecard(correctAnswers, incorrectAnswers, Math.min(quizData.length, 10));
        selection.setVisible(true);
    }

    private void loadQuizData(String subject, String level, String module) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mpr", "root", "shubham1332");

            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int subID = subject.equalsIgnoreCase("DSA") ? 1 : 2; // Assuming 1 for DSA, 2 for DBMS
            //int m = Integer.parseInt(module);
             String query = "SELECT QUES, MCQ1, MCQ2, MCQ3, MCQ4, CORRECT_ANS FROM QUESTION WHERE SUB_ID = " + subID +
                              " ORDER BY RAND() LIMIT 10";

           // String query = "SELECT QUES, MCQ1, MCQ2, MCQ3, MCQ4, CORRECT_ANS WHERE SUB_ID = " + subID + "and Module =" + m + ";";

            ResultSet rs = st.executeQuery(query);
            rs.last();
            int totalQuestions = rs.getRow();
            rs.beforeFirst(); // Reset cursor

            quizData = new String[totalQuestions][6]; // 5 MCQs + 1 Question
            correctAnswersArray = new String[totalQuestions]; // Array to hold correct answers
            userAnswers = new String[totalQuestions]; // Array to hold user answers

            int index = 0;
            while (rs.next()) {
                quizData[index][0] = rs.getString("QUES");
                quizData[index][1] = rs.getString("MCQ1");
                quizData[index][2] = rs.getString("MCQ2");
                quizData[index][3] = rs.getString("MCQ3");
                quizData[index][4] = rs.getString("MCQ4");
                correctAnswersArray[index] = rs.getString("CORRECT_ANS"); // Store the correct answer
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizPage quizPage = new QuizPage("DSA", "Level 1", "Module 1");
            quizPage.setVisible(true);
        });
    }
}