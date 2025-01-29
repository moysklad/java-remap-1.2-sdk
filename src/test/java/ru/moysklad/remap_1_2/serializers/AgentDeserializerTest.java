package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AgentDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeCounterparty() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Counterparty();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.COUNTERPARTY);

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, Agent.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + Agent.class.getName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }

        Agent parsed = objectMapperCustom.readValue(data, Agent.class);
        assertEquals(Counterparty.class, parsed.getClass());
    }

    @Test
    public void test_deserializeOrganization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Organization();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.ORGANIZATION);

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, Agent.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + Agent.class.getName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }

        Agent parsed = objectMapperCustom.readValue(data, Agent.class);
        assertEquals(Organization.class, parsed.getClass());
    }

    @Test
    public void test_deserializeEmployee() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Employee();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.EMPLOYEE);

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, Agent.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + Agent.class.getName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }
        Agent parsed = objectMapperCustom.readValue(data, Agent.class);
        assertEquals(Employee.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Counterparty();

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapperCustom.readValue(data, Agent.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'agent': meta is null"
            );
        }
    }

    @Test
    public void test_deserializeWithoutMetaType() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Counterparty();
        e.setMeta(new Meta());

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapperCustom.readValue(data, Agent.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'agent': meta.type is null"
            );
        }
    }

    @Test
    public void test_deserializeWithIncorrectMetaType() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Agent e = new Counterparty();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PRODUCT);

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapperCustom.readValue(data, Agent.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'agent': meta.type must be one of [organization, counterparty, employee]"
            );
        }
    }
}
