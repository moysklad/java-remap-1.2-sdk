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
    private String id;
    private String code;
    private String externalCode;
    private String name;
    private String description;
    private String updated;
    private Integer version;
}
