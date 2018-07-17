package com.lognex.api.entities;

import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.ProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConsignmentEntity extends MetaEntity implements ProductMarker {
    private String accountId;
    private String externalCode;
    private ConsignmentParentMarker assortment;
    private String name;
    private String id;
    private String label;
    private Integer version;
    private String updated;
}
