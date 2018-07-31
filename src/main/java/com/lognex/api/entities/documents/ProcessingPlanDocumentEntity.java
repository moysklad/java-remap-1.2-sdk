package com.lognex.api.entities.documents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
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
    private String accountId;
    private Long cost;
    private String externalCode;
    private GroupEntity group;
    private String id;
    private ListEntity materials;
    private String name;
    private EmployeeEntity owner;
    private String pathName;
    private ListEntity products;
    private Boolean shared;
    private LocalDateTime updated;
    private Integer version;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PlanItem extends MetaEntity {
        private String id;
        private String accountId;
        private ProductMarker product;
        private Double quantity;
    }
}
