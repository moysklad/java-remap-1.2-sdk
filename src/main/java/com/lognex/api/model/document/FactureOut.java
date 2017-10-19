package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractFinance;
import com.lognex.api.model.base.AbstractOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class FactureOut extends AbstractOperation implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private String stateContractId;

    private List<Demand> demands = new ArrayList<>();
    private List<AbstractFinance> payments = new ArrayList<>();
    private List<AbstractOperation> returns = new ArrayList<>();

    private Agent consignee;
    private String paymentNumber;
    private Date paymentDate;
}