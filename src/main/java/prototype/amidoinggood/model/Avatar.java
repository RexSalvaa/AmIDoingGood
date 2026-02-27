package prototype.amidoinggood.model;

public class Avatar {
    private double health;
    private double intellect;
    private double dopamine;

    private double exercise = 50.0;
    private double sleepManagement = 50.0;
    private double social = 50.0;
    private double duty = 50.0;

    private String skinColor = "edb98a";
    private String top = "shortFlat";
    private String hairColor = "4a3123";
    private String clothing = "shirtCrewNeck";

    public Avatar() {
        this.health = 50.0;
        this.intellect = 50.0;
        this.dopamine = 50.0;
    }

    public void processDailyData(
            String breakfast, String meal1, String meal2, double extras,
            boolean sport, double read, double hobbies, double study, double project,
            boolean friends, boolean fun, boolean newPeople, boolean gf, boolean laid,
            double scroll, double wasted, double games,
            double sleepHours, double achievement) {

        double healthDelta = 0;
        double intellectDelta = 0;
        double dopamineDelta = 0;
        double exerciseDelta = 0;
        double sleepDelta = 0;
        double socialDelta = 0;
        double dutyDelta = 0;

        healthDelta += calculateMealHealth(breakfast, true);
        dopamineDelta += calculateMealDopamine(breakfast);
        healthDelta += calculateMealHealth(meal1, false);
        dopamineDelta += calculateMealDopamine(meal1);
        healthDelta += calculateMealHealth(meal2, false);
        dopamineDelta += calculateMealDopamine(meal2);
        healthDelta -= (extras * 1.5);
        healthDelta -= (extras * 1.5);
        healthDelta += (sport ? 3.0 : -2.0);
        healthDelta -= (scroll * 0.5);

        intellectDelta += (read * 2.5) + (study * 3.0) + (project * 2.5) + (hobbies * 1.0);
        intellectDelta -= (scroll * 2.0) + (wasted * 3.0) + (games * 0.5);

        dopamineDelta += (fun ? 3.0 : -1.5) + (laid ? 4.0 : 0) + (gf ? 2.0 : 0) + (friends ? 2.0 : 0);
        dopamineDelta += (hobbies * 1.5) + (games * 1.0);
        dopamineDelta -= (scroll * 1.5) + (wasted * 2.5);

        exerciseDelta += (sport ? 8.0 : -3.0) + (laid ? 2.0 : 0);

        double sleepDeviation = Math.abs(8.0 - sleepHours);
        sleepDelta += (5.0 - (sleepDeviation * 2.5));
        healthDelta += (2.5 - (sleepDeviation * 1.5));

        socialDelta += (friends ? 4.0 : -2.5) + (newPeople ? 3.0 : 0) + (gf ? 3.0 : -1.0) + (fun ? 2.0 : 0);

        dutyDelta += (achievement - 50.0) * 0.25;

        addHealth(healthDelta);
        addIntellect(intellectDelta);
        addDopamine(dopamineDelta);
        addExercise(exerciseDelta);
        addSleepManagement(sleepDelta);
        addSocial(socialDelta);
        addDuty(dutyDelta);
    }


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

    public void addExercise(double amount) {
        this.exercise = Math.max(0, Math.min(100, this.exercise + amount));
    }

    public void addSleepManagement(double amount) {
        this.sleepManagement = Math.max(0, Math.min(100, this.sleepManagement + amount));
    }

    public void addSocial(double amount) {
        this.social = Math.max(0, Math.min(100, this.social + amount));
    }

    public void addDuty(double amount) {
        this.duty = Math.max(0, Math.min(100, this.duty + amount));
    }

    private void clampStats() {
        this.health = Math.max(0, Math.min(100, this.health));
        this.intellect = Math.max(0, Math.min(100, this.intellect));
        this.dopamine = Math.max(0, Math.min(100, this.dopamine));
    }

    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }

    public double getIntellect() { return intellect; }
    public void setIntellect(double intellect) { this.intellect = intellect; }

    public double getDopamine() { return dopamine; }
    public void setDopamine(double dopamine) { this.dopamine = dopamine; }

    public double getExercise() { return exercise; }
    public double getSleepManagement() { return sleepManagement; }
    public double getSocial() { return social; }
    public double getDuty() { return duty; }

    public String getSkinColor() { return skinColor; }
    public void setSkinColor(String skinColor) { this.skinColor = skinColor; }

    public String getTop() { return top; }
    public void setTop(String top) { this.top = top; }

    public String getHairColor() { return hairColor; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }

    public String getClothing() { return clothing; }
    public void setClothing(String clothing) { this.clothing = clothing; }

    private double calculateMealHealth(String meal, boolean isBreakfast) {
        if (meal == null || meal.equalsIgnoreCase("none") || meal.equalsIgnoreCase("skip")) {
            return isBreakfast ? -1.0 : -2.0; // Penalty for skipping meals
        }

        return switch (meal) {
            case "Healthy" -> isBreakfast ? 2.0 : 3.0; // Good health points
            case "Neutral" -> isBreakfast ? 1.0 : 1.0; // Minimal health points
            case "Junk" -> isBreakfast ? -3.0 : -5.0;  // Severe health penalty!
            default -> 0.0;
        };
    }

    private double calculateMealDopamine(String meal) {
        if (meal == null || meal.equalsIgnoreCase("none") || meal.equalsIgnoreCase("skip")) {
            return 0.0;
        }

        return switch (meal) {
            case "Healthy" -> 1.0; // Slight mood boost for feeling good about yourself
            case "Neutral" -> 0.0;
            case "Junk" -> 5.0;    // Massive dopamine spike from eating garbage!
            default -> 0.0;
        };
    }
}