package LinienDiagramm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse verwaltet die Datenpunkte für das Diagramm und sorgt für die Persistenz in einer Datei.
 */
public class GraphData {
    private List<Integer> dataPoints;
    private final String filename = "graphData.txt";

    /**
     * Konstruktor, der die Datenpunkte initialisiert und aus einer Textdatei lädt
     */
    public GraphData() {
        dataPoints = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Fügt einen neuen Datenpunkt hinzu und speichert die aktualisierten Daten in einer Textdatei.
     * @param summe Der neue Datenpunkt (Saldo).
     */
    public void addDataPoint(double summe) {
        dataPoints.add((int) summe);
        saveToFile();
    }

    /**
     * Gibt die Liste der Datenpunkte zurück.
     * @return die gespeicherten Datenpunkte(Linienpunkte) 
     */
    public List<Integer> getDataPoints() {
        return dataPoints;
    }

    /**
     * Speichert die aktuellen Datenpunkte in einer Textdatei.
     */
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int point : dataPoints) {
                writer.println(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt die Datenpunkte aus einer Textdatei.
     */
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataPoints.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
