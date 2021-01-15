package ru.moysklad.remap_1_2.entities.documents.positions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;
import ru.moysklad.remap_1_2.entities.products.GoodTaxSystem;

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
