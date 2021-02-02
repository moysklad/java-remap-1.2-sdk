package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;

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
