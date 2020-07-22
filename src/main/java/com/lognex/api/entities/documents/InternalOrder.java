package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternalOrder extends DocumentEntity implements IEntityWithAttributes {
    private LocalDateTime created;
    private String externalCode;
    private Organization organization;
    private ListEntity<DocumentPosition> positions;
    private Rate rate;
    private Store store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private Project project;
    private State state;
    private List<Attribute> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private List<PurchaseOrder> purchaseOrders;
    private List<Move> moves;

    public InternalOrder(String id) {
        super(id);
    }
}
