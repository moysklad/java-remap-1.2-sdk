package ru.moysklad.remap_1_2.entities.documents.positions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderDocumentPosition extends DocumentPosition {
    private Double discount;
    private Long shipped;
    private Integer vat;
    private Double inTransit;
}
