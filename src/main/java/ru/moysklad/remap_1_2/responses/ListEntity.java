package ru.moysklad.remap_1_2.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Context;
import ru.moysklad.remap_1_2.entities.Fetchable;
import ru.moysklad.remap_1_2.entities.MetaEntity;

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
