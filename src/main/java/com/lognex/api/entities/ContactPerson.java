package com.lognex.api.entities;

import com.lognex.api.entities.agents.Agent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactPerson extends MetaEntity {
    private LocalDateTime updated;
    private String description;
    private String externalCode;
    private String email;
    private String phone;
    private String position;
    private Agent agent;

    public ContactPerson(String id) {
        super(id);
    }
}
