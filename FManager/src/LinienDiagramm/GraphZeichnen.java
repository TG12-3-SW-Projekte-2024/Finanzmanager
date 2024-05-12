package LinienDiagramm;

import java.awt.Graphics;
import javax.swing.JPanel;

public class GraphZeichnen extends JPanel {
  
	private static final long serialVersionUID = 1L;
	private GraphData graphData;

    public GraphZeichnen(GraphData graphData) {
        this.graphData = graphData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Integer> dataPoints = graphData.getDataPoints();
        if (dataPoints.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int numDataPoints = dataPoints.size();
        int barWidth = width / numDataPoints;

        int maxSaldo = dataPoints.stream().max(Integer::compare).orElse(0);
        if (maxSaldo == 0) maxSaldo = 1; // To avoid division by zero

        for (int i = 0; i < numDataPoints; i++) {
            int x = i * barWidth;
            int barHeight = dataPoints.get(i) * height / maxSaldo;
            int y = height - barHeight;
            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth, barHeight);
        }
    }
}