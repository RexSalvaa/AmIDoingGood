package prototype.amidoinggood.utils; // Vérifie ton package !

import prototype.amidoinggood.model.Avatar;

public class AvatarApi {

    // On utilise le style "Pixel Art" de DiceBear (c'est léger et retro)
    private static final String BASE_URL = "https://api.dicebear.com/9.x/pixel-art/png";

    public static String getAvatarUrl(Avatar avatar) {
        String seed = "Rex"; // Ton identité unique
        String mouth = "happy";
        String eyes = "normal";

        // Logique visuelle simple (Tu pourras la complexifier)
        if (avatar.getHealth() < 30) {
            mouth = "puke"; // Malade
            eyes = "dead";
        } else if (avatar.getDopamine() < 40) {
            mouth = "sad";
            eyes = "sad";
        } else if (avatar.getIntellect() > 80) {
            eyes = "sunglasses"; // Trop cool
        }

        // Construction de l'URL
        return BASE_URL + "?seed=" + seed + "&mouth=" + mouth + "&eyes=" + eyes;
    }
}