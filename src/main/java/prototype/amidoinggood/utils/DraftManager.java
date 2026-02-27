package prototype.amidoinggood.utils;

import prototype.amidoinggood.model.DailyDraft;
import java.io.*;
import java.nio.file.*;

public class DraftManager {
    private static final String DRAFT_FILE = "daily_draft.txt";

    public static void saveDraft(DailyDraft draft) {
        try {
            String data = draft.toSaveString();

            Files.writeString(Paths.get(DRAFT_FILE), data);
            System.out.println("Draft pre-saved successfully!");
        } catch (IOException e) {
            System.err.println("Draft save error: " + e.getMessage());
        }
    }

    public static DailyDraft loadDraft() {
        try {
            if (Files.exists(Paths.get(DRAFT_FILE))) {
                String content = Files.readString(Paths.get(DRAFT_FILE));

                return DailyDraft.fromSaveString(content);
            }
        } catch (Exception e) {
            System.err.println("No valid draft found or error reading.");
        }
        return null;
    }

    public static void clearDraft() {
        try {
            Files.deleteIfExists(Paths.get(DRAFT_FILE));
        } catch (IOException e) {
            System.err.println("Failed to clear draft.");
        }
    }
}