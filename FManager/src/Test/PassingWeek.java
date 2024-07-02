package Test;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * Die Klasse PassingWeek ermöglicht die automatische Ausführung
 * der weekPassed()-Methode jede Woche zur selben Zeit.
 */
public class PassingWeek {
    
    private static final Path LAST_RUN_WEEK_FILE = Paths.get("lastRunWeek.txt");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private MainGUI dieGUI;

   
    public PassingWeek(MainGUI mainGUI) {
        this.dieGUI = mainGUI;
    }

    /**
     * Startet die Planung der wöchentlichen Ausführung.
     * 
     * @throws IOException Wenn ein Fehler beim Lesen oder Schreiben der Datei auftritt
     */
    public void startScheduling() throws IOException {
        LocalDateTime lastRun = readLastRunTime();
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        if (lastRun == null) {
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        } else {
            // Schätzt die vergangenen Wochen ein und führt sie aus.
            while (lastRun.plusWeeks(1).isBefore(now)) {
                lastRun = lastRun.plusWeeks(1);
                executeWeekPassed();
            }
        }
        scheduleNextRun();
    }

    /**
     * Plant die nächste Ausführung der weekPassed()-Methode.
     */
    private void scheduleNextRun() {
        LocalDateTime nextRun = LocalDateTime.now().plusWeeks(1).withHour(0).withMinute(0).withSecond(0);
        long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
        scheduler.schedule(this::executeWeekPassed, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Führt die weekPassed()-Methode aus und speichert die aktuelle Zeit als letzte Ausführungszeit.
     */
    private void executeWeekPassed() {
        dieGUI.weekPassed();
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
     */
    private LocalDateTime readLastRunTime() throws IOException {
        if (Files.exists(LAST_RUN_WEEK_FILE)) {
            String lastRunStr = new String(Files.readAllBytes(LAST_RUN_WEEK_FILE));
            return LocalDateTime.parse(lastRunStr);
        }
        return null; // Return null sollte die Datei nicht existieren; Bedeutet erster Run.
    }

    /**
     * Schreibt die aktuelle Ausführungszeit in die Datei.
     * 
     * @param time Die aktuelle Ausführungszeit
     * @throws IOException Wenn ein Fehler beim Schreiben der Datei auftritt
     */
    private void writeLastRunTime(LocalDateTime time) throws IOException {
        Files.write(LAST_RUN_WEEK_FILE, time.toString().getBytes());
    }
}