package Steuerung;

import LinienDiagramm.GraphData;
import LinienDiagramm.GraphGUI;
import Test.MainGUI;

public class Steuerung {

	GraphData graphData;
    private GraphGUI graphGUI;
	MainGUI dieGui;
	
	public Steuerung(MainGUI gui) {
		dieGui = gui;
		
	}
	
	public double berechneSumme(double REinkommen, double RAusgaben, double Konto) {
		

		double summe = Konto+REinkommen-RAusgaben;
			
		return summe;
		
		
	}
	public Steuerung(GraphData graphData, GraphGUI graphGUI) {
        this.graphData = graphData;
        this.graphGUI = graphGUI;
    }

	

}

    /**
     * Konstruktor, der die GraphData- und GraphGUI-Objekte übernimmt.
     * @param graphData Die Daten für das Diagramm.
     * @param graphGUI Die GUI für das Diagramm.
     */
  
