package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CashOut extends AbstractFinance implements IEntityWithAttributes {
    private Agent agent;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    private FactureIn factureIn;
    private List<AbstractOperation> operations = new ArrayList<>();

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}

