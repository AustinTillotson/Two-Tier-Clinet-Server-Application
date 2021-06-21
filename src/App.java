import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Locale;

public class App extends JFrame {

    private ResultSetTableModel tableModel;
    private Connection connection;
    private boolean connectedToDatabase = false;

    private JPanel DatabaseInfoPanel = new JPanel();
    private JLabel DatabaseInfoLabel = new JLabel("Enter Database Information");
    private JLabel DriverLabel = new JLabel("JDBC Driver");
    private JLabel DBLabel = new JLabel("Database URL");
    private JLabel UsernameLabel = new JLabel("Username");
    private JLabel PasswordLabel = new JLabel("Password");
    private String[] Drivers = {"com.mysql.cj.jdbc.Driver"};
    private JComboBox DriverInput = new JComboBox(Drivers);
    private String[] Databases = {"jdbc:mysql://localhost:3306/project2?useTimezone=true&serverTimezone=UTC",
            "jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC",
            "jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC"};
    private JComboBox DBInput = new JComboBox(Databases);
    private JTextField UsernameInput = new JTextField();
    private JPasswordField PasswordInput = new JPasswordField();

    private JPanel CommandPanel = new JPanel();
    private JLabel CommandLabel = new JLabel("Enter An SQL Command");
    private JTextArea CommandField = new JTextArea();
    private JButton CommandClearButton = new JButton("Clear SQL Command");
    private JButton CommandExecuteButton = new JButton("Execute SQL Command");

    private JPanel ConnectDBPanel = new JPanel();
    private JButton DBConnectButton = new JButton("Connect to Database");
    private JLabel ConnectionResultLabel = new JLabel("  No Connection Now");

    private JPanel ResultPanel = new JPanel();
    private JLabel ResultWindowLabel = new JLabel("SQL Execution Result Window");
    private JTable ResultWindowTable = new JTable();
    private JScrollPane ResultWindowDisplay = new JScrollPane(ResultWindowTable);
    private JButton ResultClearButton = new JButton("Clear Result Window");


    /****************************************************
     Action Listeners
     ****************************************************/

    /*****************************************************
     "Connect" Button
     ****************************************************/

    private class ConnectToDatabaseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Driver = String.valueOf(DriverInput.getSelectedItem());
            String Database = String.valueOf((DBInput.getSelectedItem()));
            String Username = UsernameInput.getText();
            String Password = PasswordInput.getText();
            MysqlDataSource dataSource = null;
            if(Username.length() == 0) {
                //System.out.println("Username must be filled out\n");
                ConnectionResultLabel.setText("  Username must be filled out");
            } else if(Password.length() == 0) {
                //System.out.println("Password must be entered\n");
                ConnectionResultLabel.setText("  Password must be entered");
            } else {
                try
                {
                    dataSource = new MysqlDataSource();
                    dataSource.setURL(Database);
                    dataSource.setUser(Username);
                    dataSource.setPassword(Password);

                    // connect to database
                    // establish connection to database
                    if(connectedToDatabase == true) {
                        try
                        {
                            connection.close();
                            ConnectionResultLabel.setText("  Connection was closed");
                            connectedToDatabase = false;
                        } catch (SQLException sqlException)
                        {
                            sqlException.printStackTrace();
                        }
                    }
                    Connection connection = dataSource.getConnection();

                    // update database connection status
                    connectedToDatabase = true;
                    ConnectionResultLabel.setText("  Connected to " + Database);

                    //**********************************************************************
                    //  Create tableModel now that valid connection has been established
                    //**********************************************************************

                    try
                    {
                        tableModel = new ResultSetTableModel(connection, connectedToDatabase, CommandField.getText());
                    } catch ( SQLException sqlException )
                    {
                        JOptionPane.showMessageDialog( null, sqlException.getMessage(),
                                "Database error", JOptionPane.ERROR_MESSAGE );

                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();

                        System.exit( 1 );   // terminate application
                    } catch ( ClassNotFoundException classNotFound )
                    {
                        JOptionPane.showMessageDialog( null,
                                "MySQL driver not found", "Driver not found",
                                JOptionPane.ERROR_MESSAGE );

                        System.exit( 1 ); // terminate application
                    }
                } //end try
                catch ( SQLException sqlException )
                {
                    sqlException.printStackTrace();
                    ConnectionResultLabel.setText(("  Invalid user"));
                } // end catch
            }
        }
    }

    /*****************************************************
     "Clear / Execute SQL Command" Button
     ****************************************************/

    private class SQLCommandListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear
            if(e.getSource() == CommandClearButton) {
                CommandField.setText("");
            }
            // Execute
            else if(e.getSource() == CommandExecuteButton) {
                //System.out.println("Executing " + CommandField.getText());
                if(CommandField.getText().toLowerCase(Locale.ROOT).contains("select"))
                {
                    //*******************
                    //   SELECT
                    //*******************
                    try {
                        tableModel.setQuery(CommandField.getText());
                        ResultWindowTable.setModel(tableModel);
                    } catch (SQLException sqlException) {
                        JOptionPane.showMessageDialog(null,
                                sqlException.getMessage(), "Database error",
                                JOptionPane.ERROR_MESSAGE);

                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();

                        //System.exit(1); // terminate application
                    } // end inner catch
                }
                else
                {
                    //*******************
                    //   UPDATE
                    //*******************
                    try {
                        tableModel.setUpdate(CommandField.getText());
                        ResultWindowTable.setModel(tableModel);
                    } catch (SQLException sqlException) {
                        JOptionPane.showMessageDialog(null,
                                sqlException.getMessage(), "Database error",
                                JOptionPane.ERROR_MESSAGE);

                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();

                        //System.exit(1); // terminate application
                    } // end inner catch
                }
            }
            // Fail case
            else {
                System.out.println("Error occurred");
                CommandField.setText("Error occurred");
            }
        }
    }

    /*****************************************************
     "Clear Result Window" Button
     ****************************************************/

    private class ClearTableListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("to do");
        }
    }

    /*****************************************************
     Application GUI
     ****************************************************/

    public App() {
        super("Project 2 - SQL Client App - (AT - CNT 4714 - Summer 2021");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(780, 500));
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
        CommandField.setBorder(BorderFactory.createLineBorder(Color.black));
        CommandPanel.add(CommandClearButton);
        CommandClearButton.setBounds(30, 130, 170, 23);
        CommandClearButton.setForeground(Color.red);
        CommandClearButton.setBackground(Color.white);
        CommandClearButton.setBorderPainted(false);
        CommandClearButton.addActionListener(new SQLCommandListener());
        CommandPanel.add(CommandExecuteButton);
        CommandExecuteButton.setBounds(230, 130, 165, 23);
        CommandExecuteButton.setBackground(Color.green);
        CommandExecuteButton.setBorderPainted(false);
        CommandExecuteButton.addActionListener(new SQLCommandListener());

        // Connect to Database Panel
        add(ConnectDBPanel);
        ConnectDBPanel.setBounds(0, 160, 750, 40);
        //ConnectDBPanel.setBackground(Color.black);
        ConnectDBPanel.setLayout(null);
        ConnectDBPanel.add(DBConnectButton);
        DBConnectButton.setBounds(15, 5, 170, 23);
        DBConnectButton.setForeground(Color.yellow);
        DBConnectButton.setBackground(Color.blue);
        DBConnectButton.setBorderPainted(false);
        DBConnectButton.addActionListener(new ConnectToDatabaseListener());
        ConnectDBPanel.add(ConnectionResultLabel);
        ConnectionResultLabel.setBounds(200, 5, 545, 23);
        ConnectionResultLabel.setForeground(Color.red);
        ConnectionResultLabel.setOpaque(true);
        ConnectionResultLabel.setBackground(Color.black);

        // SQL Execution Result Window Panel
        add(ResultPanel);
        ResultPanel.setBounds(0, 200, 750, 260);
        //ResultPanel.setBackground(Color.CYAN);
        ResultPanel.setLayout(null);
        ResultPanel.add(ResultWindowLabel);
        ResultWindowLabel.setBounds(50, 5, 200, 15);
        ResultWindowLabel.setForeground(Color.blue);
        ResultPanel.add(ResultWindowDisplay);
        ResultWindowDisplay.setBounds(50, 20, 690, 200);
        ResultWindowDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        //ResultPanel.add(ResultWindowTable);
        //ResultWindowTable.setBounds(50, 20, 690, 200);
        ResultWindowTable.setGridColor(Color.black);
        //ResultWindowTable.setBorder(BorderFactory.createLineBorder(Color.black));
        ResultPanel.add(ResultClearButton);
        ResultClearButton.setBounds(20, 228, 160, 23);
        ResultClearButton.setBackground(Color.yellow);
        ResultClearButton.setBorderPainted(false);
        ResultClearButton.addActionListener(new ClearTableListener());


        setVisible(true);

        // ensure database connection is closed when user quits application
        addWindowListener(new WindowAdapter()
                          {
                              // disconnect from database and exit when window has closed
                              public void windowClosed( WindowEvent event )
                              {
                                  tableModel.disconnectFromDatabase();
                                  System.exit( 0 );
                              } // end method windowClosed
                          } // end WindowAdapter inner class
        );
    }

    public static void main(String[] args) {
        new App();
    }
}
