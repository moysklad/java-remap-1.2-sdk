package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.IEntityWithAttributes;
import ru.moysklad.remap_1_2.entities.Rate;
import ru.moysklad.remap_1_2.entities.State;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailDrawerCashIn extends DocumentEntity implements IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime created;
    private String description;
    private String externalCode;
    private Organization organization;
    private Rate rate;
    private RetailShift retailShift;
    private String syncId;
    private LocalDateTime deleted;
    private State state;
    private List<Attribute> attributes;

    public RetailDrawerCashIn(String id) {
        super(id);
    }
}
