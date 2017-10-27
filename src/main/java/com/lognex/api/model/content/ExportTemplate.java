package com.lognex.api.model.content;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.Template;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExportTemplate extends Entity {
    private Template template;
    private Extension extension;

    public ExportTemplate(Template template, Extension extension) {
        this.template = template;
        this.extension = extension;
    }


    public enum Extension {
        XLS("xls"), PDF("pdf"), HTML("html"), ODS("ods");

        private final String value;

        Extension(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value;
        }
    }
}