package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
