package prototype.amidoinggood.controller;

import javafx.scene.control.*;
import prototype.amidoinggood.model.ActionType;
import prototype.amidoinggood.model.Avatar;
import prototype.amidoinggood.utils.AvatarApi;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import prototype.amidoinggood.utils.DataLogger;
import prototype.amidoinggood.utils.SaveManager;

public class DashboardController {

    // --- LIENS FXML ---
    @FXML private ImageView avatarView;
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar intellectBar;
    @FXML private ProgressBar dopamineBar;

    // Diet
    @FXML private ChoiceBox<String> breakfastChoice;
    @FXML private ChoiceBox<String> meal1Choice;
    @FXML private ChoiceBox<String> meal2Choice;
    @FXML private Slider extrasSlider;

    // Self Improvement
    @FXML private CheckBox sportCheck;
    @FXML private Slider readSlider;
    @FXML private Slider hobbiesSlider;

    // Professional
    @FXML private Slider studySlider;
    @FXML private Slider projectSlider;

    // Social
    @FXML private CheckBox friendsCheck;
    @FXML private CheckBox funCheck;
    @FXML private CheckBox newPeopleCheck;

    // Relationship
    @FXML private CheckBox gfCheck;
    @FXML private CheckBox laidCheck;

    // Self Destroying
    @FXML private Slider scrollSlider;
    @FXML private Slider wastedSlider;
    @FXML private Slider gamesSlider;

    // Final Mood
    @FXML private Slider happinessSlider;

    @FXML private Label extrasLabel;
    @FXML private Label readLabel;
    @FXML private Label hobbiesLabel;
    @FXML private Label studyLabel;
    @FXML private Label projectLabel;
    @FXML private Label scrollLabel;
    @FXML private Label wastedLabel;
    @FXML private Label gamesLabel;
    @FXML private Label happinessLabel;

    @FXML private ChoiceBox<String> skinChoice;
    @FXML private ChoiceBox<String> topChoice;
    @FXML private ChoiceBox<String> hairColorChoice;
    @FXML private ChoiceBox<String> clothingChoice;

    private Avatar myAvatar;

    public void initialize() {
        this.myAvatar = SaveManager.load(); // Charge l'avatar et son style

        // --- 1. INITIALISER LE STUDIO D'AVATAR (Valeurs v9 Strictes) ---

        // Peau (Pâle, Clair, Moyen, Foncé, Très foncé)
        skinChoice.getItems().addAll("ffdbb4", "edb98a", "d08b5b", "ae5d29", "614335");

        // Coupes
        topChoice.getItems().addAll("bob", "shortFlat", "shortWaved", "theCaesar", "hijab", "turban", "winterHat01");

        // Cheveux (Noir, Brun, Blond, Roux, Gris)
        hairColorChoice.getItems().addAll("2c1b18", "4a3123", "b58143", "a55728", "d5d5d5");

        // Vêtements (hoodie a été retiré, on utilise des basiques)
        clothingChoice.getItems().addAll("blazerAndShirt", "blazerAndSweater", "collarAndSweater", "graphicShirt", "shirtCrewNeck", "shirtVNeck");

        // --- 2. METTRE LES VALEURS SAUVEGARDÉES PAR DÉFAUT ---
        skinChoice.setValue(myAvatar.getSkinColor());
        topChoice.setValue(myAvatar.getTop());
        hairColorChoice.setValue(myAvatar.getHairColor());
        clothingChoice.setValue(myAvatar.getClothing());

        // --- 3. LIVE PREVIEW : METTRE À JOUR L'IMAGE DÈS QU'ON CHANGE UNE OPTION ---
        skinChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setSkinColor(newV); updateView(); SaveManager.save(myAvatar); });
        topChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setTop(newV); updateView(); SaveManager.save(myAvatar); });
        hairColorChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setHairColor(newV); updateView(); SaveManager.save(myAvatar); });
        clothingChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setClothing(newV); updateView(); SaveManager.save(myAvatar); });

        // Configurer les ChoiceBox
        String[] options = {"Healthy", "Neutral", "Junk"};
        breakfastChoice.getItems().addAll(options);
        meal1Choice.getItems().addAll(options);
        meal2Choice.getItems().addAll(options);

        breakfastChoice.setValue("Neutral");
        meal1Choice.setValue("Neutral");
        meal2Choice.setValue("Neutral");

        setupSliderLabel(readSlider, readLabel, "h");
        setupSliderLabel(hobbiesSlider, hobbiesLabel, "h");
        setupSliderLabel(studySlider, studyLabel, "h");
        setupSliderLabel(projectSlider, projectLabel, "h");
        setupSliderLabel(scrollSlider, scrollLabel, "h");
        setupSliderLabel(gamesSlider, gamesLabel, "h");

        // Pour les entiers spécifiques
        extrasSlider.valueProperty().addListener((obs, oldV, newV) ->
                extrasLabel.setText(String.valueOf(newV.intValue())));

        wastedSlider.valueProperty().addListener((obs, oldV, newV) ->
                wastedLabel.setText("Level: " + newV.intValue()));

        happinessSlider.valueProperty().addListener((obs, oldV, newV) ->
                happinessLabel.setText("Mood: " + newV.intValue() + "/10"));



        updateView();
    }
    private void setupSliderLabel(Slider slider, Label label, String unit) {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.format("%.1f%s", newValue.doubleValue(), unit));
        });
    }

    @FXML
    protected void onValidateDay() {
        // --- 1. LOGIQUE D'IMPACT SUR L'AVATAR ---

        // Impact Diet
        applyDietImpact(breakfastChoice.getValue());
        applyDietImpact(meal1Choice.getValue());
        applyDietImpact(meal2Choice.getValue());
        myAvatar.addHealth(extrasSlider.getValue() * -2); // Les extras baissent la santé

        // Impact Self Improvement
        if (sportCheck.isSelected()) {
            myAvatar.addHealth(10);
            myAvatar.addDopamine(5);
        }
        myAvatar.addIntellect(readSlider.getValue() * 1.5);
        myAvatar.addIntellect(hobbiesSlider.getValue() * 1.2);

        // Impact Professional
        myAvatar.addIntellect(studySlider.getValue() * 2);
        myAvatar.addIntellect(projectSlider.getValue() * 2);

        // Impact Social & Relationship
        if (friendsCheck.isSelected()) myAvatar.addDopamine(10);
        if (funCheck.isSelected()) myAvatar.addDopamine(10);
        if (laidCheck.isSelected()) {
            myAvatar.addDopamine(20);
            myAvatar.addHealth(5);
        }

        // Impact Self Destroying
        myAvatar.addDopamine(scrollSlider.getValue() * -3);
        myAvatar.addHealth(wastedSlider.getValue() * -4);
        myAvatar.addDopamine(gamesSlider.getValue() * -1.5);

        // --- 2. ENREGISTREMENT COMPLET DES DONNÉES (Data Science) ---
        DataLogger.logFullDay(
                breakfastChoice.getValue(), meal1Choice.getValue(), meal2Choice.getValue(),
                extrasSlider.getValue(),
                sportCheck.isSelected(), readSlider.getValue(), hobbiesSlider.getValue(),
                studySlider.getValue(), projectSlider.getValue(),
                friendsCheck.isSelected(), gfCheck.isSelected(), laidCheck.isSelected(),
                scrollSlider.getValue(), wastedSlider.getValue(), gamesSlider.getValue(),
                happinessSlider.getValue()
        );

        // --- 3. SAUVEGARDE ET MAJ ---
        SaveManager.save(myAvatar);
        updateView();

        // Optionnel : Afficher un message de confirmation
        System.out.println("Journée enregistrée avec succès !");
    }

    private void applyDietImpact(String quality) {
        switch (quality) {
            case "Healthy" -> { myAvatar.addHealth(3); myAvatar.addDopamine(1); }
            case "Junk" -> { myAvatar.addHealth(-5); myAvatar.addDopamine(5); }
        }
    }

    // --- MÉTHODES DE MISE À JOUR VISUELLE ---
    private void updateView() {

        String avatarUrl = prototype.amidoinggood.utils.AvatarApi.getAvatarUrl(myAvatar);
        System.out.println("Génération de l'avatar : " + avatarUrl); // Pratique pour cliquer dessus dans la console !

        try {
            // 1. On prépare la connexion vers l'API
            java.net.URL url = new java.net.URL(avatarUrl);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            // 2. LE TRUC MAGIQUE : On fait croire à l'API qu'on est un vrai navigateur Web (Chrome)
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            // 3. On lit l'image directement depuis le flux sécurisé
            avatarView.setImage(new Image(connection.getInputStream()));

        } catch (Exception e) {
            System.err.println("❌ Erreur de téléchargement de l'avatar : " + e.getMessage());
        }

        // --- MISE À JOUR DES BARRES ---
        healthBar.setProgress(myAvatar.getHealth() / 100.0);
        intellectBar.setProgress(myAvatar.getIntellect() / 100.0);
        dopamineBar.setProgress(myAvatar.getDopamine() / 100.0);

        updateBarColors();
    }

    private void updateBarColors() {
        if (myAvatar.getHealth() < 30) healthBar.setStyle("-fx-accent: #c0392b;");
        else healthBar.setStyle("-fx-accent: #e74c3c;");

        if (myAvatar.getDopamine() < 30) dopamineBar.setStyle("-fx-accent: #7f8c8d;");
        else dopamineBar.setStyle("-fx-accent: #f1c40f;");
    }
}