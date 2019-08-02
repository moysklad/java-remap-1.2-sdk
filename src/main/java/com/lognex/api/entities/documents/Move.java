package com.lognex.api.entities.documents;

import com.lognex.api.entities.Rate;
import com.lognex.api.entities.State;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Agent;
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
public class Move extends DocumentEntity {
    private LocalDateTime created;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Agent organization;
    private ListEntity<DocumentPosition> positions;
    private Rate rate;
    private Store sourceStore;
    private State state;
    private Store targetStore;
    private InternalOrder internalOrder;

    public Move(String id) {
        super(id);
    }
}
