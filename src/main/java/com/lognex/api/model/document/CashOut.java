package com.lognex.api.model.document;

import com.lognex.api.model.base.FinanceOut;
import com.lognex.api.model.base.Operation;
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
public class CashOut extends FinanceOut implements IEntityWithAttributes {
    private Agent agent;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;

    private FactureIn factureIn;
    private List<Operation> operations = new ArrayList<>();
}

