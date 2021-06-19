import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel DatabaseInfoPanel = new JPanel();
    private JLabel DatabaseInfoLabel = new JLabel("Enter Database Information");
    private JLabel DriverLabel = new JLabel("JDBC Driver");
    private JLabel DBLabel = new JLabel("Database URL");
    private JLabel UsernameLabel = new JLabel("Username");
    private JLabel PasswordLabel = new JLabel("Password");
    private String[] Drivers = {"to do"};
    private JComboBox DriverInput = new JComboBox(Drivers);
    private String[] Databases = {"to do"};
    private JComboBox DBInput = new JComboBox(Databases);
    private JTextField UsernameInput = new JTextField();
    private JPasswordField PasswordInput = new JPasswordField();

    private JPanel CommandPanel = new JPanel();
    private JLabel CommandLabel = new JLabel("Enter An SQL Command");
    private JTextField CommandField = new JTextField();
    private JButton CommandClearButton = new JButton("Clear SQL Command");
    private JButton CommandExecuteButton = new JButton("Execute SQL Command");

    private JPanel ConnectDBPanel = new JPanel();

    private JPanel ResultPanel = new JPanel();


    public App() {
        super("Project 2 - SQL Client App - (AT - CNT 4714 - Summer 2021");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLayout(null);

        // Enter Database Information Panel
        add(DatabaseInfoPanel);
        DatabaseInfoPanel.setLayout(null);
        DatabaseInfoPanel.setBounds(0 , 0, 330, 160);
        //DatabaseInfoPanel.setBackground(Color.BLUE);
        DatabaseInfoPanel.add(DatabaseInfoLabel);
        DatabaseInfoLabel.setBounds(5, 2, 200, 15);
        DatabaseInfoLabel.setForeground(Color.blue);
        DatabaseInfoPanel.add(DriverLabel);
        DriverLabel.setBounds(5, 17 , 105, 23);
        DriverLabel.setOpaque(true);
        DriverLabel.setBackground(Color.lightGray);
        DatabaseInfoPanel.add(DBLabel);
        DBLabel.setBounds(5, 42, 105, 23);
        DBLabel.setOpaque(true);
        DBLabel.setBackground(Color.lightGray);
        DatabaseInfoPanel.add(UsernameLabel);
        UsernameLabel.setBounds(5, 67, 105, 23);
        UsernameLabel.setOpaque(true);
        UsernameLabel.setBackground(Color.lightGray);
        DatabaseInfoPanel.add(PasswordLabel);
        PasswordLabel.setBounds(5, 92, 105, 23);
        PasswordLabel.setOpaque(true);
        PasswordLabel.setBackground(Color.lightGray);
        DatabaseInfoPanel.add(DriverInput);
        DriverInput.setBounds(112, 17, 200, 20);
        DatabaseInfoPanel.add(DBInput);
        DBInput.setBounds(112, 42, 200, 20);
        DatabaseInfoPanel.add(UsernameInput);
        UsernameInput.setBounds(112, 67, 200, 20);
        DatabaseInfoPanel.add(PasswordInput);
        PasswordInput.setBounds(112, 92, 200, 20);
        PasswordInput.setBackground(Color.white);

        // Enter An SQL Command Panel
        add(CommandPanel);
        CommandPanel.setBounds(330, 0, 420, 160);
        //CommandPanel.setBackground(Color.RED);
        CommandPanel.setLayout(null);
        CommandPanel.add(CommandLabel);
        CommandLabel.setBounds(5, 2, 200, 15);
        CommandLabel.setForeground(Color.blue);
        CommandPanel.add(CommandField);
        CommandField.setBounds(5, 17, 410, 110);
        CommandPanel.add(CommandClearButton);
        CommandClearButton.setBounds(30, 130, 170, 23);
        CommandClearButton.setForeground(Color.red);
        CommandClearButton.setBackground(Color.white);
        CommandPanel.add(CommandExecuteButton);
        CommandExecuteButton.setBounds(230, 130, 165, 23);
        CommandExecuteButton.setBackground(Color.green);

        // Connect to Database Panel
        add(ConnectDBPanel);
        ConnectDBPanel.setBounds(0, 160, 750, 40);
        //ConnectDBPanel.setBackground(Color.black);
        ConnectDBPanel.setLayout(null);

        // SQL Execution Result Window Panel
        add(ResultPanel);
        ResultPanel.setBounds(0, 200, 750, 240);
        //ResultPanel.setBackground(Color.CYAN);
        ResultPanel.setLayout(null);


        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
