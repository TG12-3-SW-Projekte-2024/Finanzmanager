package Steuerung;

import Test.MainGUI;

public class Steuerung {

	
	MainGUI dieGui;
	public Steuerung(MainGUI gui) {
		dieGui = gui;
		
		
	}
	
	public double berechneSumme(double REinkommen, double RAusgaben, double Konto) {
		

		double summe = Konto+REinkommen-RAusgaben;
				
		
		return summe;
		
		
	}
	

}
