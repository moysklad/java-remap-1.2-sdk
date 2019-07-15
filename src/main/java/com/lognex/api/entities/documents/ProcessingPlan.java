package com.lognex.api.entities.documents;

import com.lognex.api.entities.Group;
import com.lognex.api.entities.MetaEntity;
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
