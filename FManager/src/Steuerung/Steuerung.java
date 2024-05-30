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
		
		
	}
	
	public double berechneSumme(double REinkommen, double RAusgaben, double Konto) {
		

		double summe = Konto+REinkommen-RAusgaben;
				
		graphData.addDataPoint(summe);
        graphGUI.refreshGraph();
		return summe;
		
		
	}
	

    /**
     * Konstruktor, der die GraphData- und GraphGUI-Objekte übernimmt.
     * @param graphData Die Daten für das Diagramm.
     * @param graphGUI Die GUI für das Diagramm.
     */
    public Steuerung(GraphData graphData, GraphGUI graphGUI) {
        this.graphData = graphData;
        this.graphGUI = graphGUI;
    }
}
