package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class StateTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createState() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().counterparty().states().create(state);

        List<State> retrievedStates = api.entity().counterparty().metadata().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        State originalState = (State) originalEntity;
        State updatedState = (State) updatedEntity;

        assertNotEquals(originalState.getName(), updatedState.getName());
        assertEquals(changedField, updatedEntity.getName());
        assertEquals(originalState.getStateType(), updatedState.getStateType());
        assertEquals(originalState.getColor(), updatedState.getColor());
        assertEquals(originalState.getEntityType(), updatedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        State originalState = (State) originalEntity;
        State retrievedState = (State) retrievedEntity;

        assertEquals(originalState.getName(), retrievedState.getName());
        assertEquals(originalState.getStateType(), retrievedState.getStateType());
        assertEquals(originalState.getColor(), retrievedState.getColor());
        assertEquals(originalState.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    public void doDeleteTest(String unused) throws IOException, ApiClientException {
        State state = simpleEntityManager.createSimple(State.class);

        simpleEntityManager.removeSimpleFromPool(state);
        api.entity().counterparty().states().delete(state);
        List<State> retrievedStates = api.entity().counterparty().metadata().getStates();
        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);

        assertNull(retrievedState);
    }

    @Override
    public void doDeleteByIdTest(String unused) throws IOException, ApiClientException {
        State state = simpleEntityManager.createSimple(State.class);

        simpleEntityManager.removeSimpleFromPool(state);
        api.entity().counterparty().states().delete(state.getId());
        List<State> retrievedStates = api.entity().counterparty().metadata().getStates();
        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);

        assertNull(retrievedState);
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().counterparty().states();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return State.class;
    }
}
