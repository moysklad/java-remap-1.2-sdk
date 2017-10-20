package com.lognex.api.model.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EntityLegendable extends EntityInfoable {
    private String name;
    private String description;
    private String code;
    private String externalCode;
}
