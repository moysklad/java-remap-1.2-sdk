package ru.moysklad.remap_1_2.schema;

import java.util.*;

public class SchemaReport {
    private final StringBuilder allLog = new StringBuilder();
    private final List<String> chapters = new ArrayList<>();
    private final Map<StringBuilder, Boolean> chapterBuilders = new LinkedHashMap<>();
    private static final String startChapter = ">>> ";
    private static final String endChapter = "<<< ";

    public SchemaReport() {
        chapterBuilders.put(new StringBuilder(), true);
    }

    public SchemaReport log(String message) {
        innerLog(message, true);
        return this;
    }

    private void innerLog(String message, boolean user) {
        for (String s : message.split("\n")) {
            logLine(s, user);
        }
    }

    private void logLine(String message, boolean user) {
        indent();
        allLog.append(message).append("\n");
        lastChapterBuilder().append(message).append("\n");
        if (user) {
            setOut();
        }
    }

    private void setOut() {
        for (StringBuilder key : chapterBuilders.keySet()) {
            chapterBuilders.put(key, true);
        }
    }

    private void indent() {
        for (int i = 0; i < chapters.size(); i++) {
            allLog.append("\t");
            lastChapterBuilder().append("\t");
        }
    }

    private StringBuilder lastChapterBuilder() {
        Iterator<StringBuilder> iterator = chapterBuilders.keySet().iterator();
        StringBuilder sb = null;
        while (iterator.hasNext()) {
            sb = iterator.next();
        }
        return sb;
    }

    public SchemaReport chapter(String title) {
        chapterBuilders.put(new StringBuilder(), false);
        innerLog(startChapter + title, false);
        chapters.add(title);
        return this;
    }

    public SchemaReport endChapter() {
        if (chapters.isEmpty()) {
            return this;
        }
        innerLog(endChapter + chapters.remove(chapters.size() - 1), false);
        StringBuilder lastChapterBuilder = lastChapterBuilder();
        if (chapterBuilders.remove(lastChapterBuilder)) {
            lastChapterBuilder().append(lastChapterBuilder);
        }
        return this;
    }

    @Override
    public String toString() {
        return allLog.toString();
    }

    public String allLog() {
        return allLog.toString();
    }

    public String problemLog() {
        return lastChapterBuilder().toString();
    }
}
