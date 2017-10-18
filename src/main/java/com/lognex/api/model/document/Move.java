package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.money.OverheadWithDistribution;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Move extends AbstractOperationWithPositions implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private OverheadWithDistribution overhead;
    private Store sourceStore;
    private Store targetStore;
    private InternalOrder internalOrder;

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}
