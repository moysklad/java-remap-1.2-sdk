package com.lognex.api.model.document;

import com.lognex.api.model.base.ComingOutOperation;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.entity.AgentAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Demand extends ComingOutOperation<ShipmentOutPosition> {
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
}
