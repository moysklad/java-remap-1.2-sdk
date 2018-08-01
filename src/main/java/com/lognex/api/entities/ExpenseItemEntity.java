package com.lognex.api.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseItemEntity extends MetaEntity {
    private String code;
    private String externalCode;
    private String description;
    private String updated;
}
