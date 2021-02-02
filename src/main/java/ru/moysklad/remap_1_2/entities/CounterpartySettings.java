package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Настройки справочника контрагентов
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CounterpartySettings extends MetaEntity {
    public Boolean createShared;
    public UniqueCodeRules uniqueCodeRules;
}
