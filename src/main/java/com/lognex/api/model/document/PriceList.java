package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
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
public class PriceList extends AbstractOperation implements IEntityWithAttributes {

    private String priceType;
    private List<PriceListColumn> columns = new ArrayList<>();
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}

