package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.Avatar;

public class AvatarApi {

    private static final String BASE_URL = "https://api.dicebear.com/9.x/avataaars/png";

    // Change ce mot pour changer totalement de personnage de base !
    // Essaie "Alex", "Felix", "Jack" jusqu'à trouver un avatar qui te ressemble.
    private static final String SEED = "Rex";

    public static String getAvatarUrl(Avatar avatar) {
        // 1. On récupère le style choisi par l'utilisateur
        String skinColor = avatar.getSkinColor();
        String top = avatar.getTop();
        String hairColor = avatar.getHairColor();
        String clothing = avatar.getClothing();

        // 2. Expressions par défaut
        String mouth = "default";
        String eyes = "default";

        double h = avatar.getHealth();
        double d = avatar.getDopamine();

        // 3. Logique d'expression (Priorité à la santé puis dopamine)
        if (h <= 40) {
            eyes = "xDizzy";
            mouth = (d <= 30) ? "screamOpen" : "serious";
        } else if (h >= 80 && d >= 70) {
            eyes = "hearts";
            mouth = "smile";
        } else if (d >= 70) {
            mouth = "smile";
            eyes = "happy";
        } else if (d <= 30) {
            mouth = "sad";
            eyes = "cry";
        }

        // 4. On crée l'URL avec TOUS les paramètres
        return String.format("https://api.dicebear.com/9.x/avataaars/png?seed=Rex&mouth=%s&eyes=%s&skinColor=%s&top=%s&hairColor=%s&clothing=%s&backgroundColor=b6e3f4",
                mouth, eyes, skinColor, top, hairColor, clothing);
    }
}