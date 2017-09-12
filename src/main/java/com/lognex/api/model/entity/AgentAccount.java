package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityInfoable;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentAccount extends AbstractEntityInfoable {
    //agent?
    private boolean isDefault;
    private String accountNumber;
    private String bankName;
    private String bankLocation;
    private String correspondentAccount;
    private String bic;

    public AgentAccount(ID id) {
        setId(id);
    }
}
