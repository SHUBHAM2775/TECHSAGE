import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechSageMain extends JFrame {
    private JPanel mainPanel;

    public TechSageMain() {
        // Set up the main frame
        setTitle("TechSage Home");
        setSize(1650, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // Set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBounds(0, 0, 1650, 1080);
        add(mainPanel);

        // Logo Label (Replace with actual logo path)
        JLabel logoLabel = new JLabel(new ImageIcon(
                "C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\TechSage.Logo.jpg"));
        logoLabel.setBounds(520, 100, 500, 350); // Adjust these values
        mainPanel.add(logoLabel);

        // Create Account Label
        JLabel createAccountLabel = new JLabel("  Create your Account");
        createAccountLabel.setBounds(630, 480, 250, 70);
        createAccountLabel.setBackground(Color.decode("#4C00B3")); // Purple color
        createAccountLabel.setForeground(Color.WHITE);
        createAccountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable
        
        createAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openSignUpScreen();
            }
        });
        mainPanel.add(createAccountLabel);

        JLabel orLabel = new JLabel("  OR");
        orLabel.setBounds(730, 560, 250, 25);
        orLabel.setForeground(Color.GRAY);
        orLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(orLabel);
        // Continue with Email Button

        JButton emailButton = new JButton("  Continue with Email");
        emailButton.setBounds(600, 620, 325, 70);
        emailButton.setBackground(Color.decode("#4C00B3")); // Purple color
        emailButton.setForeground(Color.WHITE);
        emailButton.setFont(new Font("Arial", Font.BOLD, 22));
        emailButton.setFocusPainted(false);
        emailButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate it's clickable

        // Load and resize the icon
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Shubham Upadhyay\\OneDrive\\Desktop\\VS CODE\\Practice\\OTHER-CLG\\JAVA-MPR\\Email.Logo.png");
        Image image = originalIcon.getImage(); // transform it
        Image resizedImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // resize to desired  // dimensions (e.g., 30x30)
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Set the resized icon
        emailButton.setIcon(resizedIcon);

        // Set icon position relative to text
        emailButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Text to the right of the icon
        emailButton.setVerticalTextPosition(SwingConstants.CENTER); // Center vertically

        // Set margin around the icon (optional, for spacing)
        emailButton.setIconTextGap(0); // Set gap between icon and text

            emailButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSignInScreen();
                }
            });

        mainPanel.add(emailButton);

        setVisible(true);
    }

    // Method to open the SignUp Screen
    public void openSignUpScreen() {
        // Close current window
        this.dispose();
        // Open SignUp frame
        TechSageSignUp signUpForm = new TechSageSignUp();
        signUpForm.setVisible(true);
    }

    // Method to open the SignIn Screen
    public void openSignInScreen() {
        // Close current window
        this.dispose();
        // Open SignIn frame
        TechSageSignIn signInForm = new TechSageSignIn();
        signInForm.setVisible(true);
    }
    public static void main(String[] args) {
        new TechSageMain();

    }
}