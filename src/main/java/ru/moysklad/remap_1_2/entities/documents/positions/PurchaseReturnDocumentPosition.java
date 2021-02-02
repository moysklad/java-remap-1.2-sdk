package ru.moysklad.remap_1_2.entities.documents.positions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PurchaseReturnDocumentPosition extends DocumentPosition {
    private Double discount;
    private List<String> things;
    private Integer vat;
}
