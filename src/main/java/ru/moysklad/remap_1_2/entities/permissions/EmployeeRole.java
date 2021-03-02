package ru.moysklad.remap_1_2.entities.permissions;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeRole extends MetaEntity {
    private EmployeePermissions permissions;
}
