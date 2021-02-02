package ru.moysklad.remap_1_2.entities;

import java.io.File;

public enum ExportExtension {
    xls, pdf, html, ods;

    public static ExportExtension extractFromFile(File file) {
        String fname = file.getName();

        for (ExportExtension extension : values()) {
            if (fname.contains("." + extension.name())) return extension;
        }

        throw new IllegalArgumentException("Не получается извлечь расширение файла для экспорта из '" + fname + "'!");
    }
}
