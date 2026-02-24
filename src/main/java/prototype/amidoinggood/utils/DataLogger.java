package prototype.amidoinggood.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataLogger {
    private static final String FILE_NAME = "avatar_data_log.csv";

    public static void logFullDay(String bfast, String m1, String m2, double extras,
                                  boolean sport, double read, double hobby,
                                  double study, double proj,
                                  boolean friends, boolean gf, boolean laid,
                                  double scroll, double wasted, double games,
                                  double happiness) {

        String fileName = "full_life_data.csv";
        File file = new File(fileName);
        boolean exists = file.exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            // Si le fichier vient d'être créé, on ajoute les noms des colonnes (Header)
            if (!exists) {
                pw.println("Date,Breakfast,Meal1,Meal2,Extras,Sport,Read,Hobby,Study,Project,Friends,GF,Laid,Scroll,Wasted,Games,Happiness");
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            pw.printf("%s,%s,%s,%s,%.0f,%b,%.1f,%.1f,%.1f,%.1f,%b,%b,%b,%.1f,%.1f,%.1f,%.0f%n",
                    timestamp, bfast, m1, m2, extras, sport, read, hobby, study, proj,
                    friends, gf, laid, scroll, wasted, games, happiness);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}