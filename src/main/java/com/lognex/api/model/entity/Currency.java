package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Currency extends AbstractEntityLegendable {
    private String fullName;
    private String code;
    private String isoCode;
}
