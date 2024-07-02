package Test;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * Die Klasse PassingMonth ermöglicht die automatische Ausführung
 * der monthPassed()-Methode jeden Monat am 25. um Mitternacht.
 */
public class PassingMonth {
	
    private static final Path LAST_RUN_MONTH_FILE = Paths.get("lastRunMonth.txt");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private MainGUI dieGUI;

   
    public PassingMonth(MainGUI mainGUI) {
        this.dieGUI = mainGUI;
    }

    /**
     * Startet die Planung der monatlichen Ausführung.
     * 
     */
    public void startScheduling() throws IOException {
        LocalDateTime lastRun = readLastRunTime();
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        if (lastRun == null) {
            // Erster Run, schreibe txt Dokument
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        } else {
            // Bestimme die Zeit zwischen diesem Run und dem letzten 25.
            while (nextScheduledTime(lastRun).isBefore(now)) {
                lastRun = nextScheduledTime(lastRun);
                executeMonthPassed();
            }
            writeLastRunTime(lastRun); // Update letzte Run-Zeit nachdem alle verpassten Monate abgearbeitet wurden
        }
        scheduleNextRun();
    }

    /**
     * Bestimmt die nächste geplante Ausführungszeit
     * 
     * @param lastRun Die letzte Ausführungszeit
     * @return Die nächste geplante Ausführungszeit
     */
    private LocalDateTime nextScheduledTime(LocalDateTime lastRun) {
        LocalDateTime next = lastRun.withDayOfMonth(25);
        if (lastRun.getDayOfMonth() > 25) {
            next = next.plusMonths(1);
        }
        return next.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * Plant die nächste Ausführung der monthPassed()-Methode um Mitternacht am 25. des Monats.
     */
    private void scheduleNextRun() {
        LocalDateTime nextRun = nextScheduledTime(LocalDateTime.now());
        long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
        scheduler.schedule(this::executeMonthPassed, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Führt die monthPassed()-Methode aus und speichert die aktuelle Zeit als letzte Ausführungszeit
     */
    private void executeMonthPassed() {
        dieGUI.monthPassed();
        try {
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Liest die letzte Ausführungszeit aus der Datei.
     * 
     * @return Die letzte Ausführungszeit oder null, wenn die Datei nicht existiert
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt
     * 
     * Return null heißt erster Run
     */
    private LocalDateTime readLastRunTime() throws IOException {
        if (Files.exists(LAST_RUN_MONTH_FILE)) {
            String lastRunStr = new String(Files.readAllBytes(LAST_RUN_MONTH_FILE));
            return LocalDateTime.parse(lastRunStr);
        }
        return null; 
    }

    /**
     * Schreibt die aktuelle Ausführungszeit in die Datei.
     * 
     * @param time Die aktuelle Ausführungszeit
     * @throws IOException Wenn ein Fehler beim Schreiben der Datei auftritt
     */
    private void writeLastRunTime(LocalDateTime time) throws IOException {
        Files.write(LAST_RUN_MONTH_FILE, time.toString().getBytes());
    }
}