package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Group extends MetaEntity {
    /**
     * Порядковый номер отдела в списке
     */
    private Integer index;

    public Group(String id) {
        super(id);
    }
}
