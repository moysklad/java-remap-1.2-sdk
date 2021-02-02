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
public class EnterDocumentPosition extends DocumentPosition {
    private Country country;
    private DocumentEntity.Gtd gtd;
    private Integer overhead;
    private String reason;
    private List<String> things;
}
