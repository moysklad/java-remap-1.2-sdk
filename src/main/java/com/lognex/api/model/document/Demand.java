package com.lognex.api.model.document;

import com.lognex.api.model.base.ComingOutOperation;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Demand extends ComingOutOperation<ShipmentOutPosition> implements IEntityWithAttributes {

    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private Set<Attribute<?>> attributes = new HashSet<>();
    private String documents;
}
