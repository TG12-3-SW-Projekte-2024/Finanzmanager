package LinienDiagramm;

import java.io.*;
import java.util.*;

public class GraphData {
    private List<Double> dataPoints = new ArrayList<>();
    private static final int MAX_POINTS = 6;
    private static final String FILE_NAME = "GraphData.txt";

    public GraphData() {
        loadFromFile();
    }

    public void addDataPoint(double point) {
        if (dataPoints.size() >= MAX_POINTS) {
            dataPoints.remove(0);
        }
        dataPoints.add(point);
        saveToFile();
    }

    public List<Double> getDataPoints() {
        return dataPoints;
    }

    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (double point : dataPoints) {
                out.println(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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