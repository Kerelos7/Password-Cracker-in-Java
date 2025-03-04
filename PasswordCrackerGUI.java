
// hello my name is kerelos this sheet 1 in GUI version compressed in one file 

package information.security.assignment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordCrackerGUI extends JFrame {
    private JTextField usernameField;
    private JButton crackButton;
    private JTextArea resultArea;

    public PasswordCrackerGUI() {
        setTitle("Password Cracker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        crackButton = new JButton("Crack Password");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        add(new JLabel("Enter username:"));
        add(usernameField);
        add(crackButton);
        add(new JScrollPane(resultArea));

        crackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                resultArea.setText("");

                
                boolean dictionarySuccess = dictionaryAttack();
                if (dictionarySuccess) {
                    resultArea.append("Dictionary Attack Successful! Password found.\n");
                } else {
                    resultArea.append("Dictionary Attack Failed. Starting Brute Force Attack...\n");

                    
                    boolean bruteForceSuccess = bruteForceAttack();
                    if (bruteForceSuccess) {
                        resultArea.append("Brute Force Attack Successful! Password found.\n");
                    } else {
                        resultArea.append("Brute Force Attack Failed. Password not found.\n");
                    }
                }
            }
        });
    }

    private boolean dictionaryAttack() {
        for (String password : DICTIONARY) {
            if (password.equals(CORRECT_PASSWORD)) {
                resultArea.append("Password found in dictionary: " + password + "\n");
                return true;
            }
        }
        return false;
    }

    private boolean bruteForceAttack() {
        char[] charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int length = 5; 

        return bruteForceRecursive(charset, new StringBuilder(), length);
    }

    private boolean bruteForceRecursive(char[] charset, StringBuilder candidate, int length) {
        if (candidate.length() == length) {
            if (candidate.toString().equals(CORRECT_PASSWORD)) {
                resultArea.append("Password found: " + candidate.toString() + "\n");
                return true;
            }
            return false;
        }

        for (char c : charset) {
            candidate.append(c);
            if (bruteForceRecursive(charset, candidate, length)) {
                return true;
            }
            candidate.deleteCharAt(candidate.length() - 1);
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordCrackerGUI().setVisible(true);
            }
        });
    }

    private static final String CORRECT_PASSWORD = "honey";

   
    private static final String[] DICTIONARY = {
        "password", "123456", "qwerty", "admin", "letmein", "welcome", "hello", "monkey", "sunshine", "password1"
    };
}