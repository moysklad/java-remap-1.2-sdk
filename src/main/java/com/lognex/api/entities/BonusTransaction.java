package com.lognex.api.entities;

import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.discounts.BonusProgram;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BonusTransaction extends MetaEntity{
    private Employee owner;
    private Boolean shared;
    private LocalDateTime updated;
    private LocalDateTime created;
    private String externalCode;
    private Boolean applicable;
    private LocalDateTime moment;
    private Agent agent;
    private Group group;
    private BonusTransaction parentDocument;
    private BonusProgram bonusProgram;
    private Long bonusValue;
    private Organization organization;
    private TransactionType transactionType;

    public BonusTransaction(String id) {
        super(id);
    }

    public enum TransactionType {
        EARNING, SPENDING
    }
}
