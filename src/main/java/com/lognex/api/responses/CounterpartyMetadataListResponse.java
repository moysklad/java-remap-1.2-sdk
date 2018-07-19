package com.lognex.api.responses;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.StateEntity;
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
public final class CounterpartyMetadataListResponse extends MetadataListResponse {
    private List<AttributeEntity> attributes;
    private List<StateEntity> states;
    private List<String> groups;
    private Boolean createShared;
}
