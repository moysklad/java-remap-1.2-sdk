package ru.moysklad.remap_1_2.entities.permissions.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ScriptPermissionValue {
    private BaseScriptPermissionValue view;
    private BaseScriptPermissionValue crate;
    private BaseScriptPermissionValue update;
    private BaseScriptPermissionValue delete;
    private BaseScriptPermissionValue done;
}
