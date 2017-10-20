package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFolder extends EntityLegendable {
    protected String pathName;
    protected boolean archived;
    protected long vat;
    protected long effectiveVat;
    protected ProductFolder productFolder;

    public ProductFolder(ID id) {
        setId(id);
    }
}
