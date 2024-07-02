package LinienDiagramm;

import java.io.*;
import java.util.*;

/**
 * Die Klasse GraphData verwaltet die Datenpunkte, die im Liniendiagramm angezeigt werden.
 * Sie lädt die Datenpunkte aus einer Textdatei, speichert sie und sorgt dafür, dass dazu auch immer nur eine begrenzte Anzahl
 * von Datenpunkte gespeichert wird
 */
public class GraphData {
    private List<Double> dataPoints = new ArrayList<>();
    private static final int MAX_POINTS = 6;
    private static final String FILE_NAME = "GraphData.txt";

    /**
     * Konstruktor, der die Datenpunkte aus der Datei lädt.
     */
    public GraphData() {
        loadFromFile();
    }

    /**
     * Fügt einen neuen Datenpunkt hinzu. Wenn die maximale Anzahl von Datenpunkten erreicht ist,
     * wird der älteste Datenpunkt entfernt.
     * 
     * @param point der hinzuzufügende Datenpunkt
     */
    public void addDataPoint(double point) {
        if (dataPoints.size() >= MAX_POINTS) {
            dataPoints.remove(0);
        }
        dataPoints.add(point);
        saveToFile();
    }

    /**
     * Gibt die Liste der Datenpunkte zurück.
     * 
     * @return die Liste der Datenpunkte
     */
    public List<Double> getDataPoints() {
        return dataPoints;
    }

    /**
     * Speichert die aktuellen Datenpunkte in die Datei.
     */
    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (double point : dataPoints) {
                out.println(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt die Datenpunkte aus der Datei.
     */
    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataPoints.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
