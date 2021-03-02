package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AssortmentSettings extends MetaEntity {
    /**
     * Настройки уникальности кода для сущностей справочника
     */
    private UniqueCodeRules uniqueCodeRules;

    /**
     * Настройки правил штрихкодов для сущностей справочника
     */
    private BarcodeRules barcodeRules;

    /**
     * Создавать новые документы с меткой «Общий»
     */
    private Boolean createdShared;
}
