package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperation;
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
public class RetailDrawerCashIn extends AbstractOperation implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    private RetailShift retailShift;

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}
