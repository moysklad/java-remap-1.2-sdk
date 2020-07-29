package com.lognex.api.responses;

import com.lognex.api.entities.Context;
import com.lognex.api.entities.Fetchable;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Список сущностей
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ListEntity<T extends MetaEntity> extends MetaEntity implements Fetchable {
    private Context context;
    private List<T> rows;

    public ListEntity(T... rows) {
        this.rows = Arrays.asList(rows);
    }
}
