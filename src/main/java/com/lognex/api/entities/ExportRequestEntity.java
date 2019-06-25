package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ExportRequestEntity {
    private TemplateEntity template;
    private List<ExportRequestItemEntity> templates;
    private ExportExtension extension;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ExportRequestItemEntity {
        private TemplateEntity template;
        private int count;
    }
}
