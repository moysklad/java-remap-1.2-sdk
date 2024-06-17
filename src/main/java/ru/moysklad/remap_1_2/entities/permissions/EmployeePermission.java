package ru.moysklad.remap_1_2.entities.permissions;

import ru.moysklad.remap_1_2.entities.Entity;
import ru.moysklad.remap_1_2.entities.Group;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeePermission extends Entity {
    private Boolean isActive;
    private String login;
    private String email;
    private Group group;
    private EmployeeRole role;
    private List<String> authorizedHosts;
}
