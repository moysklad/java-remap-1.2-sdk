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
public class DemandDocumentPosition extends DocumentPosition {
    private Long cost;
    private Double discount;
    private Integer overhead;
    private List<TrackingCode> trackingCodes;
    private Integer vat;
    private List<String> things;
}
