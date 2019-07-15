package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.agents.Organization;

import java.lang.reflect.Type;

/**
 * Десериализатор поля <code>agent</code>. В зависимости от метаданных, возвращает экземпляр
 * одного из классов, наследующихся от Agent: Organization, Counterparty, Employee
 */
public class AgentDeserializer implements JsonDeserializer<Agent> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public Agent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'agent': meta is null");
        if (me.getMeta().getType() == null) throw new JsonParseException("Can't parse field 'agent': meta.type is null");

        switch (me.getMeta().getType()) {
            case ORGANIZATION:
                return context.deserialize(json, Organization.class);

            case COUNTERPARTY:
                return context.deserialize(json, Counterparty.class);

            case EMPLOYEE:
                return context.deserialize(json, Employee.class);

            default:
                throw new JsonParseException("Can't parse field 'agent': meta.type must be one of [organization, counterparty, employee]");
        }
    }
}
