package LinienDiagramm;

import java.awt.*;
import java.util.List;
import javax.swing.JPanel;

/**
 * Diese Klasse ist für das Zeichnen des Liniendiagramms zuständig.
 */
public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GraphData graphData;

    /**
     * Konstruktor, der das GraphData-Objekt initialisiert.
     * @param graphData Die Daten für das Diagramm.
     */
    public GraphPanel(GraphData graphData) {
        this.graphData = graphData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Integer> dataPoints = graphData.getDataPoints();
        if (dataPoints.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int padding = 20;
        int numPoints = dataPoints.size();

        int maxY = dataPoints.stream().max(Integer::compare).orElse(0);
        int minY = dataPoints.stream().min(Integer::compare).orElse(0);
        int rangeY = maxY - minY;

        if (rangeY == 0) rangeY = 1; // Vermeidung von Division durch Null

        int xIncrement = (width - 2 * padding) / (numPoints - 1);
        int previousX = padding;
        int previousY = height - padding - (dataPoints.get(0) - minY) * (height - 2 * padding) / rangeY;

        g.drawLine(padding, height - padding, width - padding, height - padding); // X-Achse
        g.drawLine(padding, padding, padding, height - padding); // Y-Achse

        for (int i = 1; i < numPoints; i++) {
            int x = padding + i * xIncrement;
            int y = height - padding - (dataPoints.get(i) - minY) * (height - 2 * padding) / rangeY;
            g.drawLine(previousX, previousY, x, y);
            previousX = x;
            previousY = y;
        }
    }
}
