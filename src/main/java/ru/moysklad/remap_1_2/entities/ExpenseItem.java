package ru.moysklad.remap_1_2.entities;

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
    private Boolean archived;

    public ExpenseItem(String id) {
        super(id);
    }
}
