package com.lognex.api.entities;

import com.lognex.api.entities.agents.Employee;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cashier extends MetaEntity {
    private Employee employee;
    private RetailStore retailStore;

    public Cashier(String id) {
        super(id);
    }
}
