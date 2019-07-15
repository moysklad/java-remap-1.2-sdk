package com.lognex.api.responses.metadata;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeResponse extends MetaEntity {
    private List<Attribute> attributes;
}
