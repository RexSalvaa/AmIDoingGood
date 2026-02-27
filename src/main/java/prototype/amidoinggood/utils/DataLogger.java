package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.Avatar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataLogger {

    private static final String FILE_NAME = "daily_tracker_history.csv";

    public static void logFullDay(
            String breakfast, String meal1, String meal2, double extras,
            boolean sport, double read, double hobbies, double study, double project,
            boolean friends, boolean fun, boolean newPeople, boolean gf, boolean laid,
            double scroll, double wasted, double games,
            String bedtime, String wakeTime, double sleepHours,
            double achievement, double happiness, Avatar avatar) {

        Path path = Paths.get(FILE_NAME);
        List<String> lines = new ArrayList<>();
        boolean needsHeaders = true;

        try {
            if (Files.exists(path) && Files.size(path) > 0) {
                lines = Files.readAllLines(path);
                needsHeaders = false;
            }
        } catch (IOException e) {
            System.err.println("Error reading history file: " + e.getMessage());
            return;
        }

        if (needsHeaders) {
            lines.add("Date;Time;Breakfast;Meal 1;Meal 2;Extras;Sport;Read(h);Hobbies(h);" +
                    "Study(h);Project(h);Friends;Fun;NewPeople;GF;Laid;Scroll(h);Wasted(lvl);" +
                    "Games(h);Bedtime;WakeTime;SleepHours;Achievement(%);Happiness(1-10);" +
                    "Health_Stat;Intellect_Stat;Dopamine_Stat;Exercise_Stat;Sleep_Stat;Social_Stat;Duty_Stat");
        }

        LocalDateTime now = LocalDateTime.now();
        String currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        avatar.processDailyData(
                breakfast, meal1, meal2, extras,
                sport, read, hobbies, study, project,
                friends, fun, newPeople, gf, laid,
                scroll, wasted, games,
                sleepHours, achievement
        );

        String newRow = String.format(Locale.US,
                "%s;%s;%s;%s;%s;%.1f;%b;%.1f;%.1f;%.1f;%.1f;%b;%b;%b;%b;%b;%.1f;%.1f;%.1f;%s;%s;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f",
                currentDate, time,
                breakfast, meal1, meal2, extras,
                sport, read, hobbies, study, project,
                friends, fun, newPeople, gf, laid,
                scroll, wasted, games,
                bedtime == null || bedtime.isEmpty() ? "N/A" : bedtime,
                wakeTime == null || wakeTime.isEmpty() ? "N/A" : wakeTime,
                sleepHours, achievement, happiness,
                avatar.getHealth(), avatar.getIntellect(), avatar.getDopamine(),
                avatar.getExercise(), avatar.getSleepManagement(), avatar.getSocial(), avatar.getDuty()
        );

        boolean updatedToday = false;

        if (!lines.isEmpty() && !needsHeaders) {
            String lastLine = lines.get(lines.size() - 1);

            if (lastLine.startsWith(currentDate)) {
                lines.set(lines.size() - 1, newRow);
                updatedToday = true;
            }
        }

        if (!updatedToday) {
            lines.add(newRow);
        }

        try {
            Files.write(path, lines);
        } catch (IOException e) {
            System.err.println("Error saving to Excel/CSV: " + e.getMessage());
        }
    }
}