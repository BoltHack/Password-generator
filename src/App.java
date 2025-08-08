import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class App extends JFrame {
    private JTextField maxLengthInput;
    private JLabel messageLabel;
    private JButton generateButton, copyPasswordButton;

    private String symbols, password;
    private int maxLength;

    public App() {
        setLayout(new BorderLayout());
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0x16171d));
        setLocationRelativeTo(null);
        setTitle("Генератор паролей");

        messageLabel = new JLabel("Введите желаемую длину пароля");
        messageLabel.setBackground(new Color(0x16171d));
        messageLabel.setForeground(new Color(0xffffff));
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        maxLengthInput = new JTextField();
        maxLengthInput.setBackground(new Color(0x242632));
        maxLengthInput.setBorder(new EmptyBorder(10, 10, 10, 10));
        maxLengthInput.setHorizontalAlignment(SwingConstants.CENTER);
        maxLengthInput.setForeground(new Color(0xffffff));
        maxLengthInput.setCaretColor(new Color(0xffffff));

        generateButton = new JButton("Сгенерировать пароль");
        generateButton.setBackground(new Color(0x16171d));
        generateButton.setForeground(new Color(0xffffff));
        generateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        copyPasswordButton = new JButton("");
        copyPasswordButton.setBackground(new Color(0x16171d));
        copyPasswordButton.setForeground(new Color(0xffffff));
        copyPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        copyPasswordButton.addActionListener(e -> {
            StringSelection copyText = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(copyText, null);

            JOptionPane.showMessageDialog(null, "Пароль скопирован в буфер обмена!", "Успешное выполнение!", JOptionPane.INFORMATION_MESSAGE);

            remove(copyPasswordButton);
            add(messageLabel, BorderLayout.NORTH);
            revalidate();
            repaint();
        });

        add(messageLabel, BorderLayout.NORTH);
        add(maxLengthInput, BorderLayout.CENTER);
        add(generateButton, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> generatePassword());

        setVisible(true);
    }

    private void generatePassword() {
        try {
            maxLength = Integer.parseInt(maxLengthInput.getText().trim());

            password = "";
            symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!№;%:?*()_+=";
            int symbolsLength = symbols.length();

            System.out.println("\nsymbols length: " + symbolsLength);
            System.out.println("symbols max length: " + maxLength);

            for (int i = 0; i < maxLength; i++) {
                int randomIndex = (int) Math.floor(Math.random() * symbolsLength);
                password += symbols.charAt(randomIndex);
            }

            System.out.println("\npassword length: " + password.length());
            System.out.println("password: " + password);

            if (copyPasswordButton.getParent() == null) {
                add(copyPasswordButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
            copyPasswordButton.setText("<html><div style='max-width: 100%; width: 100%; text-align: center;'>Нажмите, чтобы скопировать пароль:<br/>" + password +  "</div></html>");
            maxLengthInput.setText("");
            remove(messageLabel);
            revalidate();
            repaint();

        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
    }
}