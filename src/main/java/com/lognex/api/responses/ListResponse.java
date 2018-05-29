package com.lognex.api.responses;

import com.lognex.api.entities.Context;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;

import java.util.List;

/**
 * Список сущностей
 */
public final class ListResponse<T extends MetaEntity> {
    public Context context;
    public Meta meta;
    public List<T> rows;
}
