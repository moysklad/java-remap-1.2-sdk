package com.lognex.api.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseItem extends MetaEntity {
    private String code;
    private String externalCode;
    private String description;
    private LocalDateTime updated;

    public ExpenseItem(String id) {
        super(id);
    }
}
