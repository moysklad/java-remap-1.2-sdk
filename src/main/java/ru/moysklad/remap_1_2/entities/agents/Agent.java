package ru.moysklad.remap_1_2.entities.agents;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;
import ru.moysklad.remap_1_2.entities.Fetchable;
import ru.moysklad.remap_1_2.entities.MetaEntity;

/**
 * Сущность агентов
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "meta.type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Counterparty.class, name = "counterparty"),
        @JsonSubTypes.Type(value = Employee.class, name = "employee"),
        @JsonSubTypes.Type(value = Organization.class, name = "organization"),
})
@NoArgsConstructor
public abstract class Agent extends MetaEntity implements Fetchable {
    public Agent(String id) {
        super(id);
    }
}
