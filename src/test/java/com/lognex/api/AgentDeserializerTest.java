package com.lognex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AgentDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeCounterparty() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new CounterpartyEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.counterparty);

        String data = gson.toJson(e);

        try {
            gson.fromJson(data, AgentEntity.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (RuntimeException ex) {
            assertEquals(
                    "Failed to invoke public " + AgentEntity.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        AgentEntity parsed = gsonCustom.fromJson(data, AgentEntity.class);
        assertEquals(CounterpartyEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeOrganization() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new OrganizationEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.organization);

        String data = gson.toJson(e);

        try {
            gson.fromJson(data, AgentEntity.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (RuntimeException ex) {
            assertEquals(
                    "Failed to invoke public " + AgentEntity.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        AgentEntity parsed = gsonCustom.fromJson(data, AgentEntity.class);
        assertEquals(OrganizationEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeEmployee() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new EmployeeEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.employee);

        String data = gson.toJson(e);

        try {
            gson.fromJson(data, AgentEntity.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (RuntimeException ex) {
            assertEquals(
                    "Failed to invoke public " + AgentEntity.class.getName() + "() with no args",
                    ex.getMessage()
            );
        }

        AgentEntity parsed = gsonCustom.fromJson(data, AgentEntity.class);
        assertEquals(EmployeeEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new CounterpartyEntity();
//        e.setMeta(new Meta());
//        e.getMeta().setType(Meta.Type.employee);

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, AgentEntity.class);
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
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new CounterpartyEntity();
        e.setMeta(new Meta());
//        e.getMeta().setType(Meta.Type.employee);

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, AgentEntity.class);
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
        Gson gsonCustom = LognexApi.createGson();

        AgentEntity e = new CounterpartyEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.product);

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, AgentEntity.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'agent': meta.type must be one of [organization, counterparty, employee]"
            );
        }
    }
}
