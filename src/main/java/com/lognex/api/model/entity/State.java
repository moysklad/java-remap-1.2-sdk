package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.model.base.field.StateType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class State extends EntityLegendable {

    private Integer color;
    private StateType stateType;
}
