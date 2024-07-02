package Test;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * Die Klasse PassingDay erweitert MainGUI und ermöglicht die automatische Ausführung
 * der dayPassed()-Methode täglich um Mitternacht.
 */
public class PassingDay extends MainGUI {
	
	private static final long serialVersionUID = 1L;
	private static final Path LAST_RUN_DAY_FILE = Paths.get("lastRunDay.txt");
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private MainGUI dieGUI;

	/**
	 * Konstruktor, der eine Instanz von MainGUI entgegennimmt.
	 * 
	 * @param mainGUI Die Haupt-GUI-Anwendung
	 */
	public PassingDay(MainGUI mainGUI) {
	    this.dieGUI = mainGUI;
	}

	/**
	 * Startet die Planung der täglichen Ausführung.
	 * 
	 * @throws IOException Wenn ein Fehler beim Lesen oder Schreiben der Datei auftritt
	 */
	public void startScheduling() throws IOException {
	    LocalDateTime lastRun = readLastRunTime();
	    LocalDateTime now = LocalDate.now().atStartOfDay();

	    if (lastRun == null) {
	        writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
	    } else {
	        // Bestimme die verpasste Anzahl an Tagen
	        while (lastRun.isBefore(now)) {
	            lastRun = lastRun.plusDays(1);
	            executeDayPassed();
	        }
	    }
	    scheduleNextRun();
	}

	/**
	 * Plant die nächste Ausführung der dayPassed()-Methode
	 */
	private void scheduleNextRun() {
	    LocalDateTime nextRun = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
	    long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
	    scheduler.schedule(this::executeDayPassed, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * Führt die dayPassed()-Methode aus und speichert die aktuelle Zeit als letzte Auführungszeiz
	 */
	private void executeDayPassed() {
	    dieGUI.dayPassed();
	    try {
	        writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Liest die letzte Ausführungszeit aus der Datei
	 * 
	 * @return Die letzte Ausführungszeit oder null, wenn die Datei nicht existiert
	 * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt
	 */
	private LocalDateTime readLastRunTime() throws IOException {
	    if (Files.exists(LAST_RUN_DAY_FILE)) {
	        String lastRunStr = new String(Files.readAllBytes(LAST_RUN_DAY_FILE));
	        return LocalDateTime.parse(lastRunStr);
	    }
	    return null;
	}

	/**
	 * Schreibt die aktuelle Ausführungszeit in die Datei
	 * 
	 * @param time Die aktuelle Ausführungszeit
	 * @throws IOException Wenn ein Fehler beim Schreiben der Datei auftritt
	 */
	private void writeLastRunTime(LocalDateTime time) throws IOException {
	    Files.write(LAST_RUN_DAY_FILE, time.toString().getBytes());
	}
}