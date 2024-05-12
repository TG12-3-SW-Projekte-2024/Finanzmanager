package LinienDiagramm;

import java.util.ArrayList;
import java.util.List;

public class GraphData {
    private List<Integer> dataPoints;
    private int maxDataPoints = 6;

    public GraphData() {
        dataPoints = new ArrayList<>();
    }

    public void addDataPoint(int saldo) {
        dataPoints.add(saldo);
        if (dataPoints.size() > maxDataPoints) {
            dataPoints.remove(0); // Remove the oldest data point if there are more than maxDataPoints
        }
    }

    public List<Integer> getDataPoints() {
        return dataPoints;
    }
}
