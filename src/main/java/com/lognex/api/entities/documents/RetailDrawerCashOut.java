package com.lognex.api.entities.documents;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.IEntityWithAttributes;
import com.lognex.api.entities.Rate;
import com.lognex.api.entities.State;
import com.lognex.api.entities.agents.Agent;
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
public class RetailDrawerCashOut extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime created;
    private String description;
    private ListEntity<DocumentEntity> documents;
    private String externalCode;
    private Organization organization;
    private Rate rate;
    private RetailShift retailShift;
    private String syncId;
    private LocalDateTime deleted;
    private State state;
    private List<Attribute> attributes;

    public RetailDrawerCashOut(String id) {
        super(id);
    }
}
