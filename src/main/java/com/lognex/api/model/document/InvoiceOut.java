package com.lognex.api.model.document;

import com.lognex.api.model.base.IEntityWithAttributes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceOut extends Invoice implements IEntityWithAttributes {

    private CustomerOrder customerOrder;
    private List<Demand> demands = new ArrayList<>();
}
