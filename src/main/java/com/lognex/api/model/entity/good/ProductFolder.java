package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFolder extends EntityLegendable {

    protected boolean archived;
    protected String pathName;
    protected double vat;
    protected double effectiveVat;
    protected ProductFolder productFolder;

    public ProductFolder(ID id) {
        setId(id);
    }
}