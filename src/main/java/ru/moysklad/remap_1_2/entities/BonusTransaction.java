package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.discounts.BonusProgram;

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
    private TransactionStatus transactionStatus;
    private LocalDateTime executionDate;
    private CategoryType categoryType;

    public BonusTransaction(String id) {
        super(id);
    }

    public enum TransactionType {
        EARNING, SPENDING
    }

    public enum TransactionStatus {
        WAIT_PROCESSING, COMPLETED, CANCELED
    }

    public enum CategoryType {
        REGULAR, WELCOME
    }
}
