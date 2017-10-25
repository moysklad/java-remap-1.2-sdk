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
public class Store extends EntityLegendable implements IEntityWithAttributes {

    private boolean archived;
    private String address;
    private Store parent;
    private String pathName;
    private Set<Attribute<?>> attributes = new HashSet<>();
}
