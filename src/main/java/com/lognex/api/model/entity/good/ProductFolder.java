package com.lognex.api.model.entity.good;

import com.lognex.api.model.base.AbstractEntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFolder extends AbstractEntityInfoable {

    private boolean archived;
    private String pathName;
    private double vat;
    private double effectiveVat;
    private ProductFolder productFolder;
}
