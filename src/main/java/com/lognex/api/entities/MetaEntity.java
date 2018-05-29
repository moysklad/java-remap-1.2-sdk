package com.lognex.api.entities;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MetaEntity extends Entity {
    public Meta meta;

    public MetaEntity(Meta meta) {
        this.meta = meta;
    }
}
