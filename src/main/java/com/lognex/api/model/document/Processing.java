package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Store;
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
public class Processing extends AbstractOperation implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private double quantity;
    private double processingSum;
    private List<ProcessingMaterial> materials = new ArrayList<>();
    private List<ProcessingProduct> products = new ArrayList<>();
    private Store productsStore;
    private Store materialsStore;
    private ProcessingPlan processingPlan;
    private ProcessingOrder processingOrder;
}
