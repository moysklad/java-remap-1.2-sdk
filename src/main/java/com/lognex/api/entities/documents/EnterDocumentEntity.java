package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Оприходование
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EnterDocumentEntity extends DocumentEntity {
    private Boolean applicable;
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private MetaEntity documents;
    private String externalCode;
    private LocalDateTime moment;
    private OrganizationEntity organization;
    private ListEntity positions;
    private RateEntity rate;
    private StateEntity state;
    private StoreEntity store;
    private Long sum;
    private String syncId;
}
