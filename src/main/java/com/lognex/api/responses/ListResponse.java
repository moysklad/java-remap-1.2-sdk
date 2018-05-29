package com.lognex.api.responses;

import com.lognex.api.entities.Context;
import com.lognex.api.entities.Meta;

import java.util.List;

public final class ListResponse<T> {
    public Context context;
    public Meta meta;
    public List<T> rows;
}
