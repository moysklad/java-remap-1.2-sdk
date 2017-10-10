package com.lognex.api.model.entity;

import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Counterparty extends Agent implements IEntityWithAttributes{

    private Set<Attribute> attributes = new HashSet<>();

    public Counterparty(ID id) {
        super(id);
    }

}
