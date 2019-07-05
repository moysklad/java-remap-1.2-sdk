package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CashierEntity extends MetaEntity {
    private EmployeeEntity employee;
    private RetailStoreEntity retailStore;

    public CashierEntity(String id) {
        super(id);
    }
}
