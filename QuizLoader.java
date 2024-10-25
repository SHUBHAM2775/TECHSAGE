import javax.swing.*;

public class QuizLoader {

    // This method will load a new quiz page based on subject, module, and difficulty level
    public static void loadQuizPage(String subject, String level, String module) {
        QuizAppInterface quizPage = new QuizAppInterface();

        // Check the combination of subject, level, and module
        if (subject.equals("DBMS") && level.equals("Easy") && module.equals("Mod 2: ER")) {
            // Load DBMS Easy level, Module 2 quiz
            quizPage.loadQuizPanel(subject, level, module);
        } else if (subject.equals("DBMS") && level.equals("Medium") && module.equals("Mod 1: Normalization")) {
            // Load DBMS Medium level, Module 1 quiz
            quizPage.loadQuizPanel(subject, level, module);
        } else if (subject.equals("OS") && level.equals("Hard") && module.equals("Mod 3: Scheduling")) {
            // Load OS Hard level, Module 3 quiz
            quizPage.loadQuizPanel(subject, level, module);
        } else {
            // If no valid combination is selected, show a default or error page
            JOptionPane.showMessageDialog(null, "Invalid combination selected. Please try again.");
            return;
        }

        // Display the quiz page
        quizPage.setVisible(true);
    }
}
