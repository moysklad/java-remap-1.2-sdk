package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class ProcessingOrder extends AbstractOperationWithPositions implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private Store store;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private ProcessingPlan processingPlan;
    private Date deliveryPlannedMoment;
    private double quantity;

    private List<Processing> processings = new ArrayList<>();

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElseGet(null);
    }
}
