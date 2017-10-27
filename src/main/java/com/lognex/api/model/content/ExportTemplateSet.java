package com.lognex.api.model.content;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.Template;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ExportTemplateSet extends Entity {
    private List<TemplateWithCount> templates = new ArrayList<>();

    public ExportTemplateSet(List<TemplateWithCount> templates) {
        this.templates.addAll(templates);
    }

    @Getter
    @Setter
    public static class TemplateWithCount {
        private Template template;
        private int count;

        public TemplateWithCount(Template template, int count) {
            this.template = template;
            this.count = count;
        }
    }
}
