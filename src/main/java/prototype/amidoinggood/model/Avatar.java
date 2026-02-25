package prototype.amidoinggood.model;

public class Avatar {
    private double health;
    private double intellect;
    private double dopamine;

    private double exercise = 50.0;
    private double sleepManagement = 50.0;
    private double social = 50.0;
    private double duty = 50.0;

    // --- VARIABLES DE STYLE (Codes Hex de la v9) ---
    private String skinColor = "edb98a";  // Correspond à "light"
    private String top = "shortFlat";
    private String hairColor = "4a3123";  // Correspond à "brown"
    private String clothing = "shirtCrewNeck"; // "hoodie" remplacé par un t-shirt classique
    public Avatar() {
        this.health = 50.0;
        this.intellect = 50.0;
        this.dopamine = 50.0;

    }

    // --- NOUVELLES MÉTHODES DE MODIFICATION ---

    public void addHealth(double value) {
        this.health += value;
        clampStats();
    }

    public void addIntellect(double value) {
        this.intellect += value;
        clampStats();
    }

    public void addDopamine(double value) {
        this.dopamine += value;
        clampStats();
    }

    public double getExercise() { return exercise; }
    public double getSleepManagement() { return sleepManagement; }
    public double getSocial() { return social; }
    public double getDuty() { return duty; }

    public void addExercise(double amount) { this.exercise = Math.max(0, Math.min(100, this.exercise + amount)); }
    public void addSleepManagement(double amount) { this.sleepManagement = Math.max(0, Math.min(100, this.sleepManagement + amount)); }
    public void addSocial(double amount) { this.social = Math.max(0, Math.min(100, this.social + amount)); }
    public void addDuty(double amount) { this.duty = Math.max(0, Math.min(100, this.duty + amount)); }

    public String getSkinColor() { return skinColor; }
    public void setSkinColor(String skinColor) { this.skinColor = skinColor; }

    public String getTop() { return top; }
    public void setTop(String top) { this.top = top; }

    public String getHairColor() { return hairColor; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }

    public String getClothing() { return clothing; }
    public void setClothing(String clothing) { this.clothing = clothing; }

    // Sécurise les stats entre 0.0 et 100.0
    private void clampStats() {
        this.health = Math.max(0, Math.min(100, this.health));
        this.intellect = Math.max(0, Math.min(100, this.intellect));
        this.dopamine = Math.max(0, Math.min(100, this.dopamine));
    }

    // --- NÉCESSAIRE POUR LA SAUVEGARDE ET L'AFFICHAGE ---

    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }

    public double getIntellect() { return intellect; }
    public void setIntellect(double intellect) { this.intellect = intellect; }

    public double getDopamine() { return dopamine; }
    public void setDopamine(double dopamine) { this.dopamine = dopamine; }

    // Ton ancienne méthode performAction peut rester ici si tu l'utilises encore
    public void performAction(ActionType action) {
        // ... ton ancien code ...
        clampStats();
    }
}