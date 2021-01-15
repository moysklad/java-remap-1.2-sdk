package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
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
    public void test_deserializeCounterparty() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Counterparty();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.COUNTERPARTY);

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, Agent.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (Exception ex) {
            if (!(ex instanceof RuntimeException)) fail("Ожидалось исключение RuntimeException!");

            assertEquals(
                    "Failed to invoke public " + Agent.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        Agent parsed = gsonCustom.fromJson(data, Agent.class);
        assertEquals(Counterparty.class, parsed.getClass());
    }

    @Test
    public void test_deserializeOrganization() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Organization();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.ORGANIZATION);

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, Agent.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (RuntimeException ex) {
            assertEquals(
                    "Failed to invoke public " + Agent.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        Agent parsed = gsonCustom.fromJson(data, Agent.class);
        assertEquals(Organization.class, parsed.getClass());
    }

    @Test
    public void test_deserializeEmployee() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Employee();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.EMPLOYEE);

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, Agent.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (RuntimeException ex) {
            assertEquals(
                    "Failed to invoke public " + Agent.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        Agent parsed = gsonCustom.fromJson(data, Agent.class);
        assertEquals(Employee.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() {
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Counterparty();

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Agent.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'agent': meta is null"
            );
        }
    }

    @Test
    public void test_deserializeWithoutMetaType() {
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Counterparty();
        e.setMeta(new Meta());

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Agent.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'agent': meta.type is null"
            );
        }
    }

    @Test
    public void test_deserializeWithIncorrectMetaType() {
        Gson gsonCustom = ApiClient.createGson();

        Agent e = new Counterparty();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PRODUCT);

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Agent.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'agent': meta.type must be one of [organization, counterparty, employee]"
            );
        }
    }
}
