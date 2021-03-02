package ru.moysklad.remap_1_2.entities.permissions;

import ru.moysklad.remap_1_2.entities.Group;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeePermission {
    private Boolean isActive;
    private String login;
    private String email;
    private Group group;
    private EmployeeRole role;
}
