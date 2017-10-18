package com.lognex.api.model.base.field;

import lombok.Getter;


@Getter
public class EmbeddedCollectionRef {

    private CollectionMeta meta;

    public EmbeddedCollectionRef(CollectionMeta meta){
        this.meta = meta;
    }

}
