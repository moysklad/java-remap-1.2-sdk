package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LinkedOperation extends MetaEntity {

    private Double linkedSum;

    public LinkedOperation(MetaEntity entity, Double linkedSum) {
        this.setMeta(entity.getMeta());
        this.setId(entity.getId());
        this.setAccountId(entity.getAccountId());
        this.setName(entity.getName());
        this.linkedSum = linkedSum;
    }

}
