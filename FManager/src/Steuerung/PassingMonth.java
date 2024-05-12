package Steuerung;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

import Test.MainGUI;

public class PassingMonth {
	
    private static final Path LAST_RUN_MONTH_FILE = Paths.get("lastRunMonth.txt");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private MainGUI dieGUI;

    public PassingMonth(MainGUI mainGUI) {
        this.dieGUI = mainGUI;
    }

    public void startScheduling() throws IOException {
        LocalDateTime lastRun = readLastRunTime();
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        if (lastRun == null) {
            // Erster Run, schreibe txt Dokumnet
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        } else {
            // Bestimme die Zeit zwischen diesen Run und den Letzten 25sten
            while (nextScheduledTime(lastRun).isBefore(now)) {
                lastRun = nextScheduledTime(lastRun);
                executeMonthPassed();
            }
            writeLastRunTime(lastRun); // Update letzte run time nachdem alle verpassten monate abgearbeitet wurden
        }
        scheduleNextRun();
    }

    private LocalDateTime nextScheduledTime(LocalDateTime lastRun) {
        LocalDateTime next = lastRun.withDayOfMonth(25);
        if (lastRun.getDayOfMonth() > 25) {
            next = next.plusMonths(1);
        }
        return next.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private void scheduleNextRun() {
        LocalDateTime nextRun = nextScheduledTime(LocalDateTime.now());
        long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
        scheduler.schedule(this::executeMonthPassed, delay, TimeUnit.MILLISECONDS);
    }

    private void executeMonthPassed() {
        dieGUI.monthPassed();
        try {
            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LocalDateTime readLastRunTime() throws IOException {
        if (Files.exists(LAST_RUN_MONTH_FILE)) {
            String lastRunStr = new String(Files.readAllBytes(LAST_RUN_MONTH_FILE));
            return LocalDateTime.parse(lastRunStr);
        }
        return null; // Return null heißt erster run
    }

    private void writeLastRunTime(LocalDateTime time) throws IOException {
        Files.write(LAST_RUN_MONTH_FILE, time.toString().getBytes());
    }
}