import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel DatabaseInfoPanel = new JPanel();
    private JPanel CommandPanel = new JPanel();
    private JPanel ConnectDBPanel = new JPanel();
    private JPanel ResultPanel = new JPanel();
    private JLabel DatabaseInfoLabel = new JLabel("Enter Database Information");
    private JLabel DriverLabel = new JLabel("JDBC Driver");
    private JLabel DBLabel = new JLabel("Database URL");
    private JLabel UsernameLabel = new JLabel("Username");
    private JLabel PasswordLabel = new JLabel("Password");


    public App() {
        super("Project 2 - SQL Client App - (AT - CNT 4714 - Summer 2021");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLayout(null);

        // Enter Database Information Panel
        add(DatabaseInfoPanel);
        DatabaseInfoPanel.setLayout(null);
        DatabaseInfoPanel.setBounds(0 , 0, 340, 200);
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

        // Enter An SQL Command Panel
        add(CommandPanel);
        CommandPanel.setBounds(360, 0, 400, 200);
        //CommandPanel.setBackground(Color.RED);
        CommandPanel.setLayout(null);

        // Connect to Database Panel
        add(ConnectDBPanel);
        ConnectDBPanel.setBounds(0, 200, 760, 40);
        //ConnectDBPanel.setBackground(Color.black);
        ConnectDBPanel.setLayout(null);

        // SQL Execution Result Window Panel
        add(ResultPanel);
        ResultPanel.setBounds(0, 240, 760, 240);
        //ResultPanel.setBackground(Color.CYAN);
        ResultPanel.setLayout(null);


        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
