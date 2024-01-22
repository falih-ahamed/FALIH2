import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private Frame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel infoLabel;

    public LoginFrame(Frame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.loadUsers(); // Load users when the program starts
        initialize();
        addComponents();
        addListeners();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int initialWidth = screenSize.width / 4;
        int initialHeight = screenSize.height / 4;
        setSize(initialWidth, initialHeight);
        setLocationRelativeTo(null);

    }

    private void initialize() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        int padding = 20;  // Set your desired padding value


        // Set padding on the content pane
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(padding, padding, padding, padding));
        getContentPane().setBackground(Color.WHITE);
    }

    private void addComponents() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Set background and foreground colors for buttons
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.CYAN);

        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.CYAN);

        // Set background colors for fields
        usernameField.setBackground(Color.LIGHT_GRAY);
        passwordField.setBackground(Color.LIGHT_GRAY);

        add(createLabelPanel("Username :"));
        add(createFieldPanel(usernameField));
        add(createLabelPanel("Password :"));
        add(createFieldPanel(passwordField));
        add(new JPanel());
        add(new JPanel());
        add(createButtonPanel(loginButton));
        add(createButtonPanel(registerButton));

    }

    private JPanel createLabelPanel(String label) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(120, 30));
        panel.add(jLabel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createFieldPanel(JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        field.setPreferredSize(new Dimension(400, 30));
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        button.setPreferredSize(new Dimension(150, 50));
        panel.add(button);
        return panel;
    }

    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (mainFrame.validateLogin(username, password)) {
                    mainFrame.openShoppingCartUI();
                    dispose(); // Close the login frame
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password for login.", "login Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (mainFrame.validateRegistration(username, password)) {
                    mainFrame.getUserList().add(new User(username, password));
                    mainFrame.saveUsers(); // Save users when a new user is registered
                    JOptionPane.showMessageDialog(LoginFrame.this, "Registration successful! Please login.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password for registration.", "Registration Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    @Override
    public void dispose() {
        mainFrame.saveUsers();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame mainFrame = new Frame();
            LoginFrame loginFrame = new LoginFrame(mainFrame);
            loginFrame.setVisible(true);
        });
    }
}
