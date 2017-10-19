package com.lognex.api.model.document;

import com.lognex.api.model.base.ComingOutOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
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
public class Enter extends ComingOutOperation implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private OverheadWithDistribution overhead;
}
