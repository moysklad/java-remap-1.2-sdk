package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityLegendable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Currency  extends EntityLegendable {

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
