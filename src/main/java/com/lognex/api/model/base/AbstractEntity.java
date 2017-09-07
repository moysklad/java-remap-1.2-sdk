package com.lognex.api.model.base;

import com.lognex.api.util.ID;

public abstract class AbstractEntity {
    private ID id;
    private ID accountId;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getAccountId() {
        return accountId;
    }

    public void setAccountId(ID accountId) {
        this.accountId = accountId;
    }
}
