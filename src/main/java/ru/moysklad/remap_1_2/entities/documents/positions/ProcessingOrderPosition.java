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
public class ProcessingOrderPosition extends DocumentPosition {
    private Double reserve;
}
