package LinienDiagramm;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Test.MainGUI;

public class GraphGUI {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        GraphData graphData = new GraphData();
        GraphPanel graphPanel = new GraphPanel(graphData);

        frame.add(graphPanel);
        frame.setVisible(true);

        MainGUI dieGUI = new MainGUI();
   
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphGUI::createAndShowGUI);
    }
}

