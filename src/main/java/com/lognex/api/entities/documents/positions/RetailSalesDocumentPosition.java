package com.lognex.api.entities.documents.positions;

import com.lognex.api.entities.documents.DocumentPosition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailSalesDocumentPosition extends DocumentPosition {
    private Long cost;
    private Double discount;
    private List<String> things;
    private Integer vat;
}
