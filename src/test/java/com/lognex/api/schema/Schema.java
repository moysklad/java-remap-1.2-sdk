package com.lognex.api.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Schema {
    private String title;
    private List<SchemaField> fields = new ArrayList<>();
    private List<SchemaField> additionalFields = new ArrayList<>();
    private List<List<String>> required = new ArrayList<>();
    private List<String> defaultOrder = new ArrayList<>();
}
