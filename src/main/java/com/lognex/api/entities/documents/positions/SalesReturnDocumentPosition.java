package com.lognex.api.entities.documents.positions;

import com.lognex.api.entities.Country;
import com.lognex.api.entities.documents.DocumentEntity;
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
public class SalesReturnDocumentPosition extends DocumentPosition {
    private Long cost;
    private Country country;
    private Double discount;
    private DocumentEntity.Gtd gtd;
    private List<String> things;
    private Integer vat;
}
