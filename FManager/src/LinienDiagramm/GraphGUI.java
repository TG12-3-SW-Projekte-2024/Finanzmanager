package LinienDiagramm;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GraphGUI {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        GraphData graphData = new GraphData();
        GraphPanel graphPanel = new GraphPanel(graphData);

        frame.add(graphPanel);
        frame.setVisible(true);

        Steuerung steuerung = new Steuerung(graphData);
        steuerung.start(); // Start updating graph data
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphGUI::createAndShowGUI);
    }
}

