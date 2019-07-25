package com.lognex.api.responses.metadata;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeResponse extends MetaEntity {
    private ListEntity<Attribute> attributes;
}
