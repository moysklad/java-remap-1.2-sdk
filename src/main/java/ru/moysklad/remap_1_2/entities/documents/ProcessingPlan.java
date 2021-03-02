package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Group;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingPlan extends DocumentEntity {
    private Long cost;
    private String externalCode;
    private ListEntity<PlanItem> materials;
    private String pathName;
    private ListEntity<PlanItem> products;
    private LocalDateTime deleted;
    private Group parent;

    public ProcessingPlan(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PlanItem extends MetaEntity {
        private ProductMarker product;
        private Double quantity;
    }
}
