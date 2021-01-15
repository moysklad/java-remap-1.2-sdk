package ru.moysklad.remap_1_2.entities.documents.positions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Country;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;

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
