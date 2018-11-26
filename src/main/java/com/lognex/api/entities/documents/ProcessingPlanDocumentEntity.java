package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.products.markers.ProductMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingPlanDocumentEntity extends DocumentEntity {
    private Long cost;
    private String externalCode;
    private ListEntity<DocumentPosition> materials;
    private StoreEntity materialsStore;
    private String pathName;
    private ListEntity<DocumentPosition> products;
    private StoreEntity productsStore;
    private LocalDateTime deleted;
    private GroupEntity parent;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PlanItem extends MetaEntity {
        private ProductMarker product;
        private Double quantity;
    }
}
