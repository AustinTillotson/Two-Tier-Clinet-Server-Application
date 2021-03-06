import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

    private final JPanel DatabaseInfoPanel = new JPanel();
    private final JLabel DatabaseInfoLabel = new JLabel("Enter Database Information");
    private final JLabel DriverLabel = new JLabel("JDBC Driver");
    private final JLabel DBLabel = new JLabel("Database URL");
    private final JLabel UsernameLabel = new JLabel("Username");
    private final JLabel PasswordLabel = new JLabel("Password");
    private final String[] Drivers = {"com.mysql.cj.jdbc.Driver"};
    private final JComboBox DriverInput = new JComboBox(Drivers);
    private final String[] Databases = {"jdbc:mysql://localhost:3306/project2?useTimezone=true&serverTimezone=UTC",
            "jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC",
            "jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC"};
    private final JComboBox DBInput = new JComboBox(Databases);
    private final JTextField UsernameInput = new JTextField();
    private final JPasswordField PasswordInput = new JPasswordField();

    private final JPanel CommandPanel = new JPanel();
    private final JLabel CommandLabel = new JLabel("Enter An SQL Command");
    private final JTextArea CommandField = new JTextArea();
    private final JScrollPane CommandFieldDisplay = new JScrollPane(CommandField);
    private final JButton CommandClearButton = new JButton("Clear SQL Command");
    private final JButton CommandExecuteButton = new JButton("Execute SQL Command");

    private final JPanel ConnectDBPanel = new JPanel();
    private final JButton DBConnectButton = new JButton("Connect to Database");
    private final JLabel ConnectionResultLabel = new JLabel("  No Connection Now");

    private final JPanel ResultPanel = new JPanel();
    private final JLabel ResultWindowLabel = new JLabel("SQL Execution Result Window");
    private final JTable ResultWindowTable = new JTable();
    private final TableModel Empty = new DefaultTableModel();
    private final JScrollPane ResultWindowDisplay = new JScrollPane(ResultWindowTable);
    private final JButton ResultClearButton = new JButton("Clear Result Window");

    public static void centerFrame(JFrame frame) {
        // Get size of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine cords to center main frame
        int width = frame.getSize().width;
        int height = frame.getSize().height;
        int x = (dim.width - width) / 2;
        int y = (dim.height - height) / 2;

        frame.setLocation(x, y);
    }


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
            if(Username.length() == 0) {
                ConnectionResultLabel.setText("  Username must be filled out");
            } else if(Password.length() == 0) {
                ConnectionResultLabel.setText("  Password must be entered");
            } else if(connectedToDatabase == true) {
                JOptionPane.showMessageDialog(null,
                        "Cannot make new connection when a connection has been established. " +
                                "Please restart application to make a new connection.");
            }
            else {
                try
                {
                    // Load selected Driver
                    Class.forName(Driver);

                    // connect to database
                    connection = DriverManager.getConnection(Database, Username, Password);

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
                    } catch ( ClassNotFoundException classNotFound )
                    {
                        JOptionPane.showMessageDialog( null,
                                "MySQL driver not found", "Driver not found",
                                JOptionPane.ERROR_MESSAGE );

                        System.exit( 1 ); // terminate application
                    }
                } //end try
                catch ( ClassNotFoundException classNotFoundException)
                {
                    classNotFoundException.printStackTrace();
                    ConnectionResultLabel.setText("   Driver not found");
                }
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
                    } // end inner catch
                }
                else
                {
                    //*******************
                    //   UPDATE
                    //*******************
                    try {
                        tableModel.setUpdate(CommandField.getText());
                        ResultWindowTable.setModel(Empty);
                    } catch (SQLException sqlException) {
                        JOptionPane.showMessageDialog(null,
                                sqlException.getMessage(), "Database error",
                                JOptionPane.ERROR_MESSAGE);
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
            ResultWindowTable.setModel(Empty);
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
        centerFrame(this);

        // Enter Database Information Panel
        add(DatabaseInfoPanel);
        DatabaseInfoPanel.setLayout(null);
        DatabaseInfoPanel.setBounds(0 , 0, 330, 160);
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
        CommandPanel.setLayout(null);
        CommandPanel.add(CommandLabel);
        CommandLabel.setBounds(5, 2, 200, 15);
        CommandLabel.setForeground(Color.blue);
        CommandPanel.add(CommandFieldDisplay);
        CommandFieldDisplay.setBounds(5, 17, 410, 110);
        CommandFieldDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
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
        ResultPanel.setLayout(null);
        ResultPanel.add(ResultWindowLabel);
        ResultWindowLabel.setBounds(50, 5, 200, 15);
        ResultWindowLabel.setForeground(Color.blue);
        ResultPanel.add(ResultWindowDisplay);
        ResultWindowDisplay.setBounds(50, 20, 690, 200);
        ResultWindowDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        ResultWindowTable.setGridColor(Color.black);
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
