package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Project extends EntityLegendable implements IEntityWithAttributes {
    private boolean archived;
    private Set<Attribute<?>> attributes = new HashSet<>();
}
