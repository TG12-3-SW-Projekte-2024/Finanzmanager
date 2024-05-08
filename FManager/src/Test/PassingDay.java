package Test;

import java.time.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

public class PassingDay {
	
		private static final Path LAST_RUN_DAY_FILE = Paths.get("lastRunDay.txt");
	    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	    private MainGUI dieGUI;

	    public PassingDay(MainGUI mainGUI) {
	        this.dieGUI = mainGUI;
	    }

	    public void startScheduling() throws IOException {
	        LocalDateTime lastRun = readLastRunTime();
	        LocalDateTime now = LocalDate.now().atStartOfDay();
            ;

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

	    private void scheduleNextRun() {
	        LocalDateTime nextRun = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
	        long delay = Duration.between(LocalDateTime.now(), nextRun).toMillis();
	        scheduler.schedule(this::executeDayPassed, delay, TimeUnit.MILLISECONDS);
	    }

	    private void executeDayPassed() {
	        dieGUI.dayPassed();
	        try {
	            writeLastRunTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private LocalDateTime readLastRunTime() throws IOException {
	        if (Files.exists(LAST_RUN_DAY_FILE)) {
	            String lastRunStr = new String(Files.readAllBytes(LAST_RUN_DAY_FILE));
	            return LocalDateTime.parse(lastRunStr);
	        }
	        return null;
	    }

	    private void writeLastRunTime(LocalDateTime time) throws IOException {
	        Files.write(LAST_RUN_DAY_FILE, time.toString().getBytes());
	    }
	}

