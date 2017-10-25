package com.lognex.api.model.document;

import com.lognex.api.model.base.Finance;
import com.lognex.api.model.base.Operation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Facture extends Operation implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private List<Finance> payments = new ArrayList<>();
}
