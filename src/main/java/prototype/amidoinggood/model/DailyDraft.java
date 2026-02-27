package prototype.amidoinggood.model;

public class DailyDraft {
    public String breakfast = "none";
    public String meal1 = "none";
    public String meal2 = "none";
    public double extras = 0.0;
    public boolean sport = false;
    public double sleepHours = 8.0;
    public double read = 0.0;
    public double hobbies = 0.0;
    public double study = 0.0;
    public double project = 0.0;
    public double achievement = 50.0;
    public boolean friends = false;
    public boolean fun = false;
    public boolean newPeople = false;
    public boolean gf = false;
    public boolean laid = false;
    public double scroll = 0.0;
    public double wasted = 0.0;
    public double games = 0.0;

    public String toSaveString() {
        return breakfast + ";" + meal1 + ";" + meal2 + ";" + extras + ";" +
                sport + ";" + read + ";" + hobbies + ";" + study + ";" + project + ";" +
                friends + ";" + fun + ";" + newPeople + ";" + gf + ";" + laid + ";" +
                scroll + ";" + wasted + ";" + games + ";" + sleepHours + ";" + achievement;
    }


    public static DailyDraft fromSaveString(String data) {
        DailyDraft draft = new DailyDraft();

        if (data == null || data.trim().isEmpty()) {
            return draft;
        }

        String[] parts = data.split(";");

        if (parts.length >= 19) {
            try {
                draft.breakfast = parts[0];
                draft.meal1 = parts[1];
                draft.meal2 = parts[2];
                draft.extras = Double.parseDouble(parts[3]);

                draft.sport = Boolean.parseBoolean(parts[4]);
                draft.read = Double.parseDouble(parts[5]);
                draft.hobbies = Double.parseDouble(parts[6]);
                draft.study = Double.parseDouble(parts[7]);
                draft.project = Double.parseDouble(parts[8]);

                draft.friends = Boolean.parseBoolean(parts[9]);
                draft.fun = Boolean.parseBoolean(parts[10]);
                draft.newPeople = Boolean.parseBoolean(parts[11]);
                draft.gf = Boolean.parseBoolean(parts[12]);
                draft.laid = Boolean.parseBoolean(parts[13]);

                draft.scroll = Double.parseDouble(parts[14]);
                draft.wasted = Double.parseDouble(parts[15]);
                draft.games = Double.parseDouble(parts[16]);
                draft.sleepHours = Double.parseDouble(parts[17]);
                draft.achievement = Double.parseDouble(parts[18]);
            } catch (Exception e) {
                System.err.println("Error parsing draft data: " + e.getMessage());
            }
        }

        return draft;
    }
}