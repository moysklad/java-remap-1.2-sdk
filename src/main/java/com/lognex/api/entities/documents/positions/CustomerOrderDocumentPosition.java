package com.lognex.api.entities.documents.positions;

import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.entities.products.GoodTaxSystem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOrderDocumentPosition extends DocumentPosition {
    private Double discount;
    private Double reserve;
    private Long shipped;
    private GoodTaxSystem taxSystem;
    private Integer vat;
}
