package Test;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

public class PassingWeek {
    
    private static final Path LAST_RUN_WEEK_FILE = Paths.get("lastRunWeek.txt");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private MainGUI dieGUI;

    public PassingWeek(MainGUI mainGUI) {
        this.dieGUI = mainGUI;
    }

    public void startScheduling() throws IOException {
        LocalDateTime lastRun = readLastRunTime();
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        if (lastRun == null) {
            // Erster Run, schreibe das txt Dokument.
        	writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        } else {
            // Schätzt die Vergangenen Wochen ein, und executed diese dann.
            while (lastRun.plusWeeks(1).isBefore(now)) {
                lastRun = lastRun.plusWeeks(1);
                executeWeekPassed();
            }
        }
        scheduleNextRun();
    }

    private void scheduleNextRun() {
        LocalDateTime nextRun = LocalDateTime.now().plusWeeks(1).withHour(0).withMinute(0).withSecond(0);
        long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
        scheduler.schedule(this::executeWeekPassed,delay, TimeUnit.MILLISECONDS);
    }

    private void executeWeekPassed() {
        dieGUI.weekPassed();
        try {
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LocalDateTime readLastRunTime() throws IOException {
        if (Files.exists(LAST_RUN_WEEK_FILE)) {
            String lastRunStr = new String(Files.readAllBytes(LAST_RUN_WEEK_FILE));
            return LocalDateTime.parse(lastRunStr);
        }
        return null; // Return null sollte die Datei nicht existieren; Bedeutet erster RUN.
    }

    private void writeLastRunTime(LocalDateTime time) throws IOException {
        Files.write(LAST_RUN_WEEK_FILE, time.toString().getBytes());
    }
}
