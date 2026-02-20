package prototype.amidoinggood.model;

public class Avatar {

    private double health;
    private double intellect;
    private double social;
    private double dopamine;

    public Avatar() {
        this.health = 100.0;
        this.intellect = 50.0;
        this.social = 80.0;
        this.dopamine = 70.0;
    }

    public void performAction(ActionType action) {
        System.out.println("Action effectuée : " + action);

        switch (action) {
            case EAT_BURGER -> {
                this.health -= 5.0;
                this.dopamine += 15.0;
                this.intellect -= 1.0;
            }
            case EAT_SALAD -> {
                this.health += 5.0;
                this.dopamine -= 2.0;
            }
            case CODE_JAVA -> {
                this.intellect += 10.0;
                this.social -= 5.0;
                this.dopamine += 5.0;
            }
            case SPORT -> {
                this.health += 10.0;
                this.dopamine += 10.0;
                this.social += 2.0;
            }
            case SCROLL_TIKTOK -> {
                this.intellect -= 5.0;
                this.dopamine += 20.0;
                this.social -= 2.0;
            }
        }
        clampStats(); // On s'assure que rien ne dépasse 0 ou 100
    }

    public void applyDailyDecay() {
        this.health *= 0.99;
        this.intellect *= 0.95;
        this.social *= 0.90;
        this.dopamine *= 0.80;

        clampStats();
    }

    private void clampStats() {
        this.health = Math.max(0, Math.min(100, this.health));
        this.intellect = Math.max(0, Math.min(100, this.intellect));
        this.social = Math.max(0, Math.min(100, this.social));
        this.dopamine = Math.max(0, Math.min(100, this.dopamine));
    }

    public double getHealth() { return health; }
    public double getIntellect() { return intellect; }
    public double getSocial() { return social; }
    public double getDopamine() { return dopamine; }
}