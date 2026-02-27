package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.Avatar;
import java.io.*;
import java.nio.file.*;

public class SaveManager {
    private static final String SAVE_FILE = "avatar_save.txt";

    public static void save(Avatar avatar) {
        try {
            String data = avatar.getHealth() + ";"
                    + avatar.getIntellect() + ";"
                    + avatar.getDopamine() + ";"
                    + avatar.getExercise() + ";"
                    + avatar.getSleepManagement() + ";"
                    + avatar.getSocial() + ";"
                    + avatar.getDuty() + ";"
                    + avatar.getSkinColor() + ";"
                    + avatar.getTop() + ";"
                    + avatar.getHairColor() + ";"
                    + avatar.getClothing();
            Files.writeString(Paths.get(SAVE_FILE), data);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    public static Avatar load() {
        try {
            if (Files.exists(Paths.get(SAVE_FILE))) {
                String content = Files.readString(Paths.get(SAVE_FILE));
                String[] parts = content.split(";");

                Avatar loadedAvatar = new Avatar();

                loadedAvatar.setHealth(Double.parseDouble(parts[0]));
                loadedAvatar.setIntellect(Double.parseDouble(parts[1]));
                loadedAvatar.setDopamine(Double.parseDouble(parts[2]));

                if (parts.length >= 11) {
                    loadedAvatar.addExercise(Double.parseDouble(parts[3]) - 50.0); // Adjusting for the base 50.0 init
                    loadedAvatar.addSleepManagement(Double.parseDouble(parts[4]) - 50.0);
                    loadedAvatar.addSocial(Double.parseDouble(parts[5]) - 50.0);
                    loadedAvatar.addDuty(Double.parseDouble(parts[6]) - 50.0);

                    loadedAvatar.setSkinColor(parts[7]);
                    loadedAvatar.setTop(parts[8]);
                    loadedAvatar.setHairColor(parts[9]);
                    loadedAvatar.setClothing(parts[10]);
                }
                else if (parts.length >= 7) {
                    loadedAvatar.setSkinColor(parts[3]);
                    loadedAvatar.setTop(parts[4]);
                    loadedAvatar.setHairColor(parts[5]);
                    loadedAvatar.setClothing(parts[6]);
                }
                return loadedAvatar;
            }
        } catch (Exception e) {
            System.err.println("Load error, creating a new avatar.");
        }
        return new Avatar();
    }
}