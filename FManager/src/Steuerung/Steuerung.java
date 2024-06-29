package Steuerung;

import LinienDiagramm.GraphData;
import LinienDiagramm.GraphGUI;
import Test.MainGUI;

public class Steuerung {
    private GraphData graphData;
    private GraphGUI graphGUI;
    MainGUI dieGui;

    public Steuerung(MainGUI gui) {
        dieGui = gui;
        graphData = new GraphData();
        graphGUI = new GraphGUI();
        graphGUI.setGraphData(graphData);
    }

    public double berechneSumme(double REinkommen, double RAusgaben, double Konto) {
        double summe = Konto + REinkommen - RAusgaben;
        return summe;
    }
}
