package com.lognex.api.entities;

import com.lognex.api.entities.agents.AgentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactPersonEntity extends MetaEntity {
    private LocalDateTime updated;
    private String description;
    private String externalCode;
    private String email;
    private String phone;
    private String position;
    private AgentEntity agent;

    public ContactPersonEntity(String id) {
        super(id);
    }
}
