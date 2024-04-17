package Test;

public class Steuerung {

	
	TestGUI dieGui;
	public Steuerung(TestGUI gui) {
		dieGui = gui;
		
		
	}
	
	
	public double berechneSumme(double REinkommen, double RAusgaben, double Konto) {
		

		double summe = Konto+REinkommen-RAusgaben;
				
		
		return summe;
		
		
	}

}
