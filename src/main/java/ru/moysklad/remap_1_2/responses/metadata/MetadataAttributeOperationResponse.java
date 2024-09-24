package ru.moysklad.remap_1_2.responses.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.AttributeCustomEntity;
import ru.moysklad.remap_1_2.entities.AttributeOperation;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeOperationResponse extends MetaEntity {
    private ListEntity<AttributeOperation> attributes;
}
