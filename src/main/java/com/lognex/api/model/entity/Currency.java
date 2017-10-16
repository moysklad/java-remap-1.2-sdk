package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Currency  extends AbstractEntityInfoable {

    private String fullName;
    private String isoCode;
    private double multiplicity;
    private boolean indirect;
    private CurrencyRateUpdateType rateUpdateType;
    private CurrencyUnit majorUnit;
    private CurrencyUnit minorUnit;
    private boolean archived;
    private boolean isSystem;
    private boolean isDeafult;
}
