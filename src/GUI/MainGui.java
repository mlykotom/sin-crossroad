package GUI;

import Agents.WorldAgent;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGui extends JFrame {
    private final WorldAgent mWorldAgent;
    private final List<AgentController> mCarAgents;
    private final List<AgentController> mCrossRoadAgents;

    private JPanel contentPane;
    private JButton button;

    public static MainGui runGUI(WorldAgent worldAgent, List<AgentController> carAgents, List<AgentController> crossRoadAgents) {
        MainGui mainGui = new MainGui(worldAgent, carAgents, crossRoadAgents);
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            java.lang.Runtime.getRuntime().addShutdownHook(new Thread(mainGui::closeApp));
            mainGui.setup();
        });
        return mainGui;
    }

    public void closeApp() {
        Thread thread = new Thread(() -> {
            // TODO may clean something on shutdown
        });
        thread.start();
    }

    private MainGui(WorldAgent worldAgent, List<AgentController> carAgents, List<AgentController> crossRoadAgents) {
        mWorldAgent = worldAgent;
        mCarAgents = carAgents;
        mCrossRoadAgents = crossRoadAgents;
    }

    public void setup() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Visualization of crossroads");
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

    public void invalidate() {
    }
}
