package com.lognex.api.model.document;

import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class RetailDrawerCashOperation {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    private RetailShift retailShift;
}
