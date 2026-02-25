package prototype.amidoinggood.controller;

import javafx.scene.control.*;
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

    // Les 7 Barres de stats
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar intellectBar;
    @FXML private ProgressBar dopamineBar;
    @FXML private ProgressBar exerciseBar;
    @FXML private ProgressBar sleepBar;
    @FXML private ProgressBar socialBar;
    @FXML private ProgressBar dutyBar;

    // Health & Sleep
    @FXML private TextField sleepTimeField;  // NEW
    @FXML private TextField wakeTimeField;   // NEW
    @FXML private Slider sleepSlider;
    @FXML private Label sleepLabel;
    @FXML private ChoiceBox<String> breakfastChoice;
    @FXML private ChoiceBox<String> meal1Choice;
    @FXML private ChoiceBox<String> meal2Choice;
    @FXML private Slider extrasSlider;
    @FXML private Label extrasLabel;

    // Self Improvement
    @FXML private CheckBox sportCheck;
    @FXML private Slider readSlider;
    @FXML private Label readLabel;
    @FXML private Slider hobbiesSlider;
    @FXML private Label hobbiesLabel;

    // Professional
    @FXML private Slider studySlider;
    @FXML private Label studyLabel;
    @FXML private Slider projectSlider;
    @FXML private Label projectLabel;

    // Social & Relationship
    @FXML private CheckBox friendsCheck;
    @FXML private CheckBox funCheck;
    @FXML private CheckBox newPeopleCheck;
    @FXML private CheckBox gfCheck;
    @FXML private CheckBox laidCheck;

    // Self Destroying
    @FXML private Slider scrollSlider;
    @FXML private Label scrollLabel;
    @FXML private Slider wastedSlider;
    @FXML private Label wastedLabel;
    @FXML private Slider gamesSlider;
    @FXML private Label gamesLabel;

    // End of Day Reviews
    @FXML private Slider achievementSlider;  // NEW
    @FXML private Label achievementLabel;    // NEW
    @FXML private Slider happinessSlider;
    @FXML private Label happinessLabel;

    // Avatar Customization
    @FXML private ChoiceBox<String> skinChoice;
    @FXML private ChoiceBox<String> topChoice;
    @FXML private ChoiceBox<String> hairColorChoice;
    @FXML private ChoiceBox<String> clothingChoice;

    private Avatar myAvatar;

    public void initialize() {
        this.myAvatar = SaveManager.load();
        if (this.myAvatar == null) {
            this.myAvatar = new Avatar();
        }

        // --- STUDIO D'AVATAR ---
        skinChoice.getItems().addAll("ffdbb4", "edb98a", "d08b5b", "ae5d29", "614335");
        topChoice.getItems().addAll("bob", "shortFlat", "shortWaved", "theCaesar", "hijab", "turban", "winterHat01");
        hairColorChoice.getItems().addAll("2c1b18", "4a3123", "b58143", "a55728", "d5d5d5");
        clothingChoice.getItems().addAll("blazerAndShirt", "blazerAndSweater", "collarAndSweater", "graphicShirt", "shirtCrewNeck", "shirtVNeck");

        skinChoice.setValue(myAvatar.getSkinColor());
        topChoice.setValue(myAvatar.getTop());
        hairColorChoice.setValue(myAvatar.getHairColor());
        clothingChoice.setValue(myAvatar.getClothing());

        skinChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setSkinColor(newV); updateView(); SaveManager.save(myAvatar); });
        topChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setTop(newV); updateView(); SaveManager.save(myAvatar); });
        hairColorChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setHairColor(newV); updateView(); SaveManager.save(myAvatar); });
        clothingChoice.valueProperty().addListener((obs, oldV, newV) -> { myAvatar.setClothing(newV); updateView(); SaveManager.save(myAvatar); });

        // --- CHOIX DIET ---
        String[] options = {"Healthy", "Neutral", "Junk"};
        breakfastChoice.getItems().addAll(options); meal1Choice.getItems().addAll(options); meal2Choice.getItems().addAll(options);
        breakfastChoice.setValue("Neutral"); meal1Choice.setValue("Neutral"); meal2Choice.setValue("Neutral");

        // --- SETUP LABELS DES SLIDERS ---
        setupSliderLabel(sleepSlider, sleepLabel, "h");
        sleepSlider.setValue(8.0);

        setupSliderLabel(readSlider, readLabel, "h");
        setupSliderLabel(hobbiesSlider, hobbiesLabel, "h");
        setupSliderLabel(studySlider, studyLabel, "h");
        setupSliderLabel(projectSlider, projectLabel, "h");
        setupSliderLabel(scrollSlider, scrollLabel, "h");
        setupSliderLabel(gamesSlider, gamesLabel, "h");

        extrasSlider.valueProperty().addListener((obs, oldV, newV) -> extrasLabel.setText(String.valueOf(newV.intValue())));
        wastedSlider.valueProperty().addListener((obs, oldV, newV) -> wastedLabel.setText("Level: " + newV.intValue()));
        happinessSlider.valueProperty().addListener((obs, oldV, newV) -> happinessLabel.setText("Mood: " + newV.intValue() + "/10"));

        // NEW: Achievement Label Listener
        achievementSlider.setValue(50);
        achievementSlider.valueProperty().addListener((obs, oldV, newV) ->
                achievementLabel.setText("Achieved: " + newV.intValue() + "%"));

        updateView();
    }

    private void setupSliderLabel(Slider slider, Label label, String unit) {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.format("%.1f%s", newValue.doubleValue(), unit));
        });
    }

    @FXML
    protected void onValidateDay() {
        // --- 1. AVATAR IMPACT LOGIC ---
        applyDietImpact(breakfastChoice.getValue());
        applyDietImpact(meal1Choice.getValue());
        applyDietImpact(meal2Choice.getValue());
        myAvatar.addHealth(extrasSlider.getValue() * -2);

        double sleep = sleepSlider.getValue();
        if (sleep >= 7 && sleep <= 9) { myAvatar.addSleepManagement(10); myAvatar.addHealth(5); }
        else if (sleep < 5) { myAvatar.addSleepManagement(-15); myAvatar.addHealth(-10); }

        if (sportCheck.isSelected()) { myAvatar.addHealth(10); myAvatar.addDopamine(5); myAvatar.addExercise(20); }
        else { myAvatar.addExercise(-5); }

        myAvatar.addIntellect(readSlider.getValue() * 1.5);
        myAvatar.addIntellect(hobbiesSlider.getValue() * 1.2);
        myAvatar.addIntellect(studySlider.getValue() * 2);
        myAvatar.addDuty(studySlider.getValue() * 3);
        myAvatar.addDuty(projectSlider.getValue() * 3);

        if (friendsCheck.isSelected()) { myAvatar.addDopamine(10); myAvatar.addSocial(15); }
        if (funCheck.isSelected()) myAvatar.addDopamine(10);
        if (newPeopleCheck.isSelected()) myAvatar.addSocial(10);
        if (laidCheck.isSelected()) { myAvatar.addDopamine(20); myAvatar.addHealth(5); myAvatar.addSocial(5); }

        myAvatar.addDopamine(scrollSlider.getValue() * -3);
        myAvatar.addDuty(scrollSlider.getValue() * -2);
        myAvatar.addHealth(wastedSlider.getValue() * -4);
        myAvatar.addDuty(wastedSlider.getValue() * -3);
        myAvatar.addDopamine(gamesSlider.getValue() * -1.5);
        myAvatar.addDuty(gamesSlider.getValue() * -1.5);

        // --- 2. LOG DATA TO EXCEL/CSV ---
        DataLogger.logFullDay(
                breakfastChoice.getValue(), meal1Choice.getValue(), meal2Choice.getValue(), extrasSlider.getValue(),
                sportCheck.isSelected(), readSlider.getValue(), hobbiesSlider.getValue(),
                studySlider.getValue(), projectSlider.getValue(),
                friendsCheck.isSelected(), funCheck.isSelected(), newPeopleCheck.isSelected(), gfCheck.isSelected(), laidCheck.isSelected(),
                scrollSlider.getValue(), wastedSlider.getValue(), gamesSlider.getValue(),
                sleepTimeField.getText(), wakeTimeField.getText(), sleepSlider.getValue(),
                achievementSlider.getValue(), happinessSlider.getValue(),
                myAvatar // Pass the avatar so we can log the final stats!
        );

        // --- 3. SAVE AND UPDATE UI ---
        SaveManager.save(myAvatar);
        updateView();

        // Show a quick popup to confirm it worked
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Day Saved Successfully!");
        alert.setHeaderText(null);
        alert.show();
    }
    @FXML
    protected void onOpenHistory() {
        try {
            java.io.File file = new java.io.File("daily_tracker_history.csv");
            if (file.exists()) {
                // This command tells Windows/Mac to open the file with its default program (usually Excel)
                java.awt.Desktop.getDesktop().open(file);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No history found. Finish a day first!");
                alert.setHeaderText(null);
                alert.show();
            }
        } catch (Exception e) {
            System.err.println("Could not open file: " + e.getMessage());
        }
    }

    private void applyDietImpact(String quality) {
        switch (quality) {
            case "Healthy" -> { myAvatar.addHealth(3); myAvatar.addDopamine(1); }
            case "Junk" -> { myAvatar.addHealth(-5); myAvatar.addDopamine(5); }
        }
    }

    // --- MÉTHODES DE MISE À JOUR VISUELLE ---
    private void updateView() {
        String avatarUrl = AvatarApi.getAvatarUrl(myAvatar);

        try {
            java.net.URL url = new java.net.URL(avatarUrl);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            avatarView.setImage(new Image(connection.getInputStream()));
        } catch (Exception e) {
            System.err.println("❌ Erreur de téléchargement : " + e.getMessage());
        }

        // --- MISE À JOUR DES 7 BARRES ---
        healthBar.setProgress(myAvatar.getHealth() / 100.0);
        intellectBar.setProgress(myAvatar.getIntellect() / 100.0);
        dopamineBar.setProgress(myAvatar.getDopamine() / 100.0);
        exerciseBar.setProgress(myAvatar.getExercise() / 100.0);
        sleepBar.setProgress(myAvatar.getSleepManagement() / 100.0);
        socialBar.setProgress(myAvatar.getSocial() / 100.0);
        dutyBar.setProgress(myAvatar.getDuty() / 100.0);

        updateBarColors();
    }

    private void updateBarColors() {
        if (myAvatar.getHealth() < 30) healthBar.setStyle("-fx-accent: #c0392b;");
        else healthBar.setStyle("-fx-accent: #e74c3c;");

        if (myAvatar.getDopamine() < 30) dopamineBar.setStyle("-fx-accent: #7f8c8d;");
        else dopamineBar.setStyle("-fx-accent: #f1c40f;");

        exerciseBar.setStyle("-fx-accent: #e67e22;");
        sleepBar.setStyle("-fx-accent: #9b59b6;");
        socialBar.setStyle("-fx-accent: #e84393;");
        dutyBar.setStyle("-fx-accent: #3498db;");
    }
}