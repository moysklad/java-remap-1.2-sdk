package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ExportRequest {
    private Template template;
    private List<ExportRequestItem> templates;
    private ExportExtension extension;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ExportRequestItem {
        private Template template;
        private int count;
    }
}
