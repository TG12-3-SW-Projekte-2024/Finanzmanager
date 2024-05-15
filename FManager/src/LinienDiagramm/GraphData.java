package LinienDiagramm;

import java.util.ArrayList;
import java.util.List;

public class GraphData {
    private List<Integer> dataPoints;
    private int maxDataPoints = 6;

    public GraphData() {
        dataPoints = new ArrayList<>();
    }

    /**
     * Dem Liniendiagramm wird eine neue Linie hinzugefügt
     * Aber nur, wenn Zeit vergangen ist
     * 
     * @param saldo 
     * die Differenz zwischen Einkommen und Ausgaben ergeben den nächsten Punkt
     */
    public void addDataPoint(int saldo) {
        dataPoints.add(saldo);
        if (dataPoints.size() > maxDataPoints) {
            dataPoints.remove(0); 
        }
    }

    public List<Integer> getDataPoints() {
        return dataPoints;
    }
}
