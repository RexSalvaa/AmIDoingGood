package prototype.amidoinggood.controller;

import prototype.amidoinggood.model.ActionType;
import prototype.amidoinggood.model.Avatar;
import prototype.amidoinggood.utils.AvatarApi;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController {

    // --- LIENS AVEC LE FXML ---
    @FXML private ImageView avatarView;
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar intellectBar;
    @FXML private ProgressBar dopamineBar;

    // --- LE MODÈLE ---
    private Avatar myAvatar;

    // Cette méthode est appelée automatiquement au chargement
    public void initialize() {
        this.myAvatar = new Avatar();
        updateView();
    }

    // --- ACTIONS DES BOUTONS ---
    @FXML
    protected void onEatBurger() {
        myAvatar.performAction(ActionType.EAT_BURGER);
        updateView();
    }

    @FXML
    protected void onEatSalad() {
        myAvatar.performAction(ActionType.EAT_SALAD);
        updateView();
    }

    @FXML
    protected void onCodeJava() {
        myAvatar.performAction(ActionType.CODE_JAVA);
        updateView();
    }

    @FXML
    protected void onScrollTiktok() {
        myAvatar.performAction(ActionType.SCROLL_TIKTOK);
        updateView();
    }

    // --- MISE A JOUR DE L'ECRAN ---
    // --- MISE A JOUR DE L'ECRAN ---
    // --- MISE A JOUR DE L'ECRAN ---
    private void updateView() {
        // 1. Déterminer l'état physique (BODY)
        String bodyState = "normal"; // par défaut
        if (myAvatar.getHealth() > 80) {
            bodyState = "strong";
        } else if (myAvatar.getHealth() < 40) { // Si santé basse = prise de poids (logique de jeu)
            bodyState = "fat";
        }

        // 2. Déterminer l'émotion (MOOD)
        String moodState = "normal"; // par défaut
        if (myAvatar.getDopamine() > 70) {
            moodState = "happy";
        } else if (myAvatar.getDopamine() < 30) {
            moodState = "sad";
        }

        // 3. Construire le nom du fichier
        // Ex: "fat" + "_" + "happy" => "fat_happy"
        String imageName = bodyState + "_" + moodState;
        String extension = ".png";

        // GESTION D'EXCEPTION : Ton image strong_happy est un JPG !
        if (imageName.equals("strong_happy")) {
            extension = ".jpg";
        }

        String fullPath = "/images/" + imageName + extension;

        // 4. Charger l'image
        try {
            var imageStream = getClass().getResourceAsStream(fullPath);
            if (imageStream != null) {
                avatarView.setImage(new Image(imageStream));
            } else {
                System.err.println("❌ Image introuvable : " + fullPath);
                // Fallback : remettre l'image de base si l'image spécifique manque
                avatarView.setImage(new Image(getClass().getResourceAsStream("/images/normal_normal.png")));
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image: " + e.getMessage());
        }

        // 5. Mettre à jour les barres (Code habituel)
        healthBar.setProgress(myAvatar.getHealth() / 100.0);
        intellectBar.setProgress(myAvatar.getIntellect() / 100.0);
        dopamineBar.setProgress(myAvatar.getDopamine() / 100.0);

        // Couleurs des barres
        updateBarColors();
    }

    // Ajoute cette petite méthode bonus pour que les barres changent de couleur
    private void updateBarColors() {
        if (myAvatar.getHealth() < 30) healthBar.setStyle("-fx-accent: #c0392b;"); // Rouge foncé
        else healthBar.setStyle("-fx-accent: #e74c3c;"); // Rouge normal

        if (myAvatar.getDopamine() < 30) dopamineBar.setStyle("-fx-accent: #7f8c8d;"); // Gris triste
        else dopamineBar.setStyle("-fx-accent: #f1c40f;"); // Jaune
    }
}