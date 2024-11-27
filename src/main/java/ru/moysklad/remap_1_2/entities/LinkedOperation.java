package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LinkedOperation extends MetaEntity {

    private double linkedSum;

    public LinkedOperation(DocumentEntity entity, double linkedSum) {
        this.setMeta(entity.getMeta());
        this.setId(entity.getId());
        this.setAccountId(entity.getAccountId());
        this.setName(entity.getName());
        this.linkedSum = linkedSum;
    }

}
