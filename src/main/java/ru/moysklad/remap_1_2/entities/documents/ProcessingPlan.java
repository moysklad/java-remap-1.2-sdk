package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Group;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProcessingProcess;
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
    private String pathName;
    private LocalDateTime deleted;
    private String description;
    private boolean archived;
    private Group parent;
    private ProcessingProcess processingProcess;
    private ListEntity<ProductItem> materials;
    private ListEntity<ProductItem> products;
    private ListEntity<StageItem> stages;

    public ProcessingPlan(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ProductItem extends MetaEntity {
        private ProcessingProcess.ProcessPosition processingProcessPosition;
        private ProductMarker assortment;
        private Double quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class StageItem extends MetaEntity {
        private ProcessingProcess.ProcessPosition processingProcessPosition;
        private Long cost;
        private Long labourCost;
    }
}
