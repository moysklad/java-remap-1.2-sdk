package com.lognex.api.responses;

import com.lognex.api.entities.ContextEntity;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Список сущностей
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ListEntity<T extends MetaEntity> extends MetaEntity {
    private ContextEntity context;
    private List<T> rows;
}
