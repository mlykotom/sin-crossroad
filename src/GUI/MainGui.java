package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by raven on 25.11.2016.
 */
public class MainGui extends JFrame {
    private JPanel contentPane;
    private JButton button;

    public MainGui() {
        setBounds(100, 100, 500, 500);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        button = new JButton("Button 1");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        contentPane.add(button, constraints);
    }
}
