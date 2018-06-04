package com.lognex.api.responses;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.State;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Список Метаданных Контрагентов
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CounterpartyMetadataListResponse extends MetaEntity {
    private List<Attribute> attributes;
    private List<State> states;
    private List<String> groups;
    private Boolean createShared;
}
