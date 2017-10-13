package com.lognex.api.model.base.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class CollectionMeta {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String href;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    private String mediaType = "application/json";

    private int size;
    private int limit;
    private int offset;

    public CollectionMeta(String href, String type, int size, int limit, int offset){
        this.href = href;
        this.type = type;
        this.size = size;
        this.limit = limit;
        this.offset = offset;
    }
}
