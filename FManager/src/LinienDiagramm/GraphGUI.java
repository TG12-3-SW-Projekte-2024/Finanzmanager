package LinienDiagramm;

import javax.swing.*;

import Test.MainGUI;

import java.awt.*;

/**
 * Diese Klasse erstellt das GUI und integriert das GraphPanel.
 */
public class GraphGUI {
    private GraphData graphData;
    private GraphPanel graphPanel;

    public void addSaldoDataPoint(double summe) {
		GraphData data = new GraphData();
        GraphGUI gui = new GraphGUI(data);
        int saldo = (int) summe;
        data.addDataPoint(saldo);
        data.addDataPoint(saldo);
		
	}
    /**
     * Konstruktor, der das GraphData-Objekt übernimmt und das GUI erstellt.
     * @param graphData Die Daten für das Diagramm.
     */
    public GraphGUI(GraphData graphData) {
        this.graphData = graphData;
        createAndShowGUI();
    }

    /**
     * Erstellt und zeigt das GUI.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Liniendiagramm Beispiel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        graphPanel = new GraphPanel(graphData);
        frame.add(graphPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    /**
     * Aktualisiert das Diagramm, indem das Panel neu gezeichnet wird.
     */
    public void refreshGraph() {
        graphPanel.repaint();
    }

    public static void main(String[] args) {
        
        
         Timer timer = new Timer(1000, e -> {
        	 GraphData data = new GraphData();
             GraphGUI gui = new GraphGUI(data);

             // Simulation monatlicher Aktualisierungen für Testzwecke
                 data.addDataPoint((int) (Math.random() * 20000 - 10000)); // Zufallswerte zwischen -10000 und 10000
                 gui.refreshGraph();
             });
             timer.start();
        // Simulation monatlicher Aktualisierungen für Testzwecke
        
    }

	
}
