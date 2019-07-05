package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class InternalOrderDocumentEntity extends DocumentEntity implements IEntityWithAttributes {
    private LocalDateTime created;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private OrganizationEntity organization;
    private ListEntity<DocumentPosition> positions;
    private RateEntity rate;
    private StoreEntity store;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private ProjectEntity project;
    private StateEntity state;
    private List<AttributeEntity> attributes;
    private LocalDateTime deliveryPlannedMoment;
    private List<PurchaseOrderDocumentEntity> purchaseOrders;
    private List<MoveDocumentEntity> moves;

    public InternalOrderDocumentEntity(String id) {
        super(id);
    }
}
