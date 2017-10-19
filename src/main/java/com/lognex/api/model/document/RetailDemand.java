package com.lognex.api.model.document;

import com.lognex.api.model.base.AbstractOperationWithPositions;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.RetailStore;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RetailDemand extends AbstractOperationWithPositions implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
    private String fiscalPrinterInfo;
    private int documentNumber;
    private int checkNumber;
    private double checkSum;
    private boolean fiscal;
    private int sessionNumber;
    private int ofdCode;
    private double payedSum;
    private RetailStore retailStore;
    private CustomerOrder customerOrder;
    private RetailShift retailShift;
    private double cashSum;
    private double noCashSum;
}
