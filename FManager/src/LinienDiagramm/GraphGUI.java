package LinienDiagramm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphGUI extends JFrame {
    private GraphData graphData;

    /**
     * Konstruktor, der das GUI für das Liniendiagramm initialisiert.
     */
    public GraphGUI() {
        setTitle("Liniendiagramm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    
    public void setGraphData(GraphData graphData) {
        this.graphData = graphData;
    }

    /**
     * Fügt einen neuen Datenpunkt hinzu und aktualisiert das Diagramm.
     * 
     * @param newDataPoint der neue Datenpunkt
     */
    public void updateGraph(double newDataPoint) {
        if (graphData != null) {
            graphData.addDataPoint(newDataPoint);
            repaint();
        }
    }

    /**
     * Zeichnet das Liniendiagramm neu.
     * 
     * @param g das Graphics-Objekt zum Zeichnen
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (graphData != null) {
            List<Double> dataPoints = graphData.getDataPoints();
            if (dataPoints.size() > 1) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int padding = 50;
                int labelPadding = 25;

                double xScale = ((double) width - 2 * padding - labelPadding) / (dataPoints.size() - 1);
                double maxDataPoint = dataPoints.stream().max(Double::compare).get();
                double yScale = ((double) height - 2 * padding - labelPadding) / maxDataPoint;

                List<Point> graphPoints = new ArrayList<>();
                for (int i = 0; i < dataPoints.size(); i++) {
                    int x1 = (int) (i * xScale + padding + labelPadding);
                    int y1 = (int) ((maxDataPoint - dataPoints.get(i)) * yScale + padding);
                    graphPoints.add(new Point(x1, y1));
                }

                g2d.setColor(Color.BLACK);
                g2d.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
                g2d.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);

                g2d.setColor(Color.BLUE);
                for (int i = 0; i < graphPoints.size() - 1; i++) {
                    int x1 = graphPoints.get(i).x;
                    int y1 = graphPoints.get(i).y;
                    int x2 = graphPoints.get(i + 1).x;
                    int y2 = graphPoints.get(i + 1).y;
                    g2d.drawLine(x1, y1, x2, y2);
                }

                g2d.setColor(Color.RED);
                for (Point point : graphPoints) {
                    int x = point.x - 2;
                    int y = point.y - 2;
                    int ovalW = 4;
                    int ovalH = 4;
                    g2d.fillOval(x, y, ovalW, ovalH);
                }
            }
        }
    }
}
