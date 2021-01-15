package ru.moysklad.remap_1_2.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchemaField {
    private String name;
    private String type;
    private String format;
    private List<SchemaField> fields = new ArrayList<>();

    private boolean updatable;
    private boolean nullable;

    private List<String> filters = new ArrayList<>();
    private boolean orderable;

    private Schema expand;

    @Override
    public String toString() {
        return "SchemaField{" + name + "}";
    }
}
