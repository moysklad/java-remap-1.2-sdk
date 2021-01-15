package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomEntityElement extends MetaEntity {
    private LocalDateTime updated;
    private String code;
    private String description;
    private String externalCode;

    private String customDictionaryId;

    public CustomEntityElement(String id, String customDictionaryId) {
        super(id);
        this.customDictionaryId = customDictionaryId;
    }
}
