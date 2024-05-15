package Test;

import java.io.IOException;

/**
 * Jede benötigte Klasse und Methode wird ausgeführt.
 */
public class StartApp {
    public static void main(String[] args) throws IOException {
    	//test
    	
        MainGUI mainGUI = new MainGUI();
        PassingMonth scheduler1 = new PassingMonth(mainGUI);
        PassingWeek scheduler2 = new PassingWeek(mainGUI);
        PassingDay scheduler3 = new PassingDay(mainGUI);
        scheduler1.startScheduling();
        scheduler2.startScheduling();
        scheduler3.startScheduling();
        mainGUI.setVisible(true);
        
        
        
    }
}

