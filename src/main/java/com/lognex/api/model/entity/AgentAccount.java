package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityInfoable;
import com.lognex.api.util.ID;

public class AgentAccount extends AbstractEntityInfoable {
    //agent?
    private boolean isDefault;
    private String accountNumber;
    private String bankName;
    private String bankLocation;
    private String correspondentAccount;
    private String bic;

    public AgentAccount() {}

    public AgentAccount(ID id) {
        setId(id);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public String getCorrespondentAccount() {
        return correspondentAccount;
    }

    public void setCorrespondentAccount(String correspondentAccount) {
        this.correspondentAccount = correspondentAccount;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
