package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.Avatar;
import java.io.*;
import java.nio.file.*;

public class SaveManager {
    private static final String SAVE_FILE = "avatar_save.txt";

    public static void save(Avatar avatar) {
        try {
            // On ajoute le style à la suite des stats, séparé par des points-virgules
            String data = avatar.getHealth() + ";" + avatar.getIntellect() + ";" + avatar.getDopamine() + ";"
                    + avatar.getSkinColor() + ";" + avatar.getTop() + ";" + avatar.getHairColor() + ";" + avatar.getClothing();
            Files.writeString(Paths.get(SAVE_FILE), data);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
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

                // Si on a sauvegardé le style (pour éviter les crashs avec tes anciennes sauvegardes)
                if (parts.length > 3) {
                    loadedAvatar.setSkinColor(parts[3]);
                    loadedAvatar.setTop(parts[4]);
                    loadedAvatar.setHairColor(parts[5]);
                    loadedAvatar.setClothing(parts[6]);
                }
                return loadedAvatar;
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement, création d'un nouvel avatar.");
        }
        return new Avatar();
    }
}