import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel DatabaseInfoPanel = new JPanel();
    private JPanel CommandPanel = new JPanel();
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
        DatabaseInfoPanel.setBounds(0 , 0, 340, 200);
        //DatabaseInfoPanel.setBackground(Color.BLUE);

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
