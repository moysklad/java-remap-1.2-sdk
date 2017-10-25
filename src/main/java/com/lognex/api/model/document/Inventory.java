package com.lognex.api.model.document;

import com.lognex.api.model.base.OperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Inventory extends OperationWithPositions implements IEntityWithAttributes {
    private Store store;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
}