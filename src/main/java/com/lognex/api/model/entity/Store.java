package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Store extends AbstractEntityLegendable {

    private boolean archived;
    private String address;
    private Store parent;
    private String pathName;
    private List<Attribute> attributes;
}
