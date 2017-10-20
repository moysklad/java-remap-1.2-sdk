package com.lognex.api.model.document;

import com.lognex.api.model.base.IEntityWithAttributes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceIn extends Invoice implements IEntityWithAttributes {

    private PurchaseOrder purchaseOrder;
    private List<Supply> supplies;

}