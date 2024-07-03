package LinienDiagramm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse GraphData verwaltet die Daten für die Graphen.
 */
public class GraphData {
    
    private static final String FILE_NAME = "graphData.txt";
    private List<Double> dataPoints;

    public GraphData() {
        dataPoints = new ArrayList<>();
        loadGraphData();
    }

    /**
     * Lädt die Graph-Daten aus einer Datei.
     */
    private void loadGraphData() {
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataPoints.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stellt sicher, dass die Datei existiert, und erstellt sie bei Bedarf.
     */
    private void ensureFileExists() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fügt einen neuen Datenpunkt hinzu und speichert die Daten.
     *
     * @param dataPoint Der neue Datenpunkt
     */
    public void addDataPoint(double dataPoint) {
        dataPoints.add(dataPoint);
        saveGraphData();
    }

    /**
     * Speichert die Graph-Daten in einer Datei.
     */
    private void saveGraphData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Double dataPoint : dataPoints) {
                out.println(dataPoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt die Liste der Datenpunkte zurück.
     *
     * @return Die Liste der Datenpunkte
     */
    public List<Double> getDataPoints() {
        return dataPoints;
    }
}
