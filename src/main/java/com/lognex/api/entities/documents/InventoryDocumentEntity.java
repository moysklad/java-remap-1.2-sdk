package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class InventoryDocumentEntity extends DocumentEntity {
    private MetaEntity documents;
    private String externalCode;
    private LocalDateTime moment;
    private OrganizationEntity organization;
    private ListEntity positions;
    private StoreEntity store;
    private Long sum;
}
