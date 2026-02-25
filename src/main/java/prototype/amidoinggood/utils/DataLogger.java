package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.Avatar;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        File file = new File(FILE_NAME);

        // Bulletproof check: Print headers if the file doesn't exist OR if it is completely empty (0 bytes)
        boolean needsHeaders = !file.exists() || file.length() == 0;

        try (FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw)) {

            if (needsHeaders) {
                pw.println("Date;Time;Breakfast;Meal 1;Meal 2;Extras;Sport;Read(h);Hobbies(h);" +
                        "Study(h);Project(h);Friends;Fun;NewPeople;GF;Laid;Scroll(h);Wasted(lvl);" +
                        "Games(h);Bedtime;WakeTime;SleepHours;Achievement(%);Happiness(1-10);" +
                        "Health_Stat;Intellect_Stat;Dopamine_Stat;Exercise_Stat;Sleep_Stat;Social_Stat;Duty_Stat");
            }

            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Use Locale.US to force dots for decimals, preventing Excel from getting confused
            String row = String.format(Locale.US,
                    "%s;%s;%s;%s;%s;%.1f;%b;%.1f;%.1f;%.1f;%.1f;%b;%b;%b;%b;%b;%.1f;%.1f;%.1f;%s;%s;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f",
                    date, time,
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

            pw.println(row);

        } catch (Exception e) {
            System.err.println("Error saving to Excel/CSV: " + e.getMessage());
        }
    }
}