package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ExpenseItemEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ExpenseItemEntity e = new ExpenseItemEntity();
        e.setName("expenseitem_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().expenseitem().post(e);

        ListEntity<ExpenseItemEntity> updatedEntitiesList = api.entity().expenseitem().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ExpenseItemEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ExpenseItemEntity e = createSimpleExpenseItem();

        ExpenseItemEntity retrievedEntity = api.entity().expenseitem().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().expenseitem().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ExpenseItemEntity e = createSimpleExpenseItem();

        ExpenseItemEntity retrievedOriginalEntity = api.entity().expenseitem().get(e.getId());
        String name = "expenseitem_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().expenseitem().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        e = createSimpleExpenseItem();
        retrievedOriginalEntity = api.entity().expenseitem().get(e.getId());
        name = "expenseitem_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().expenseitem().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ExpenseItemEntity e = createSimpleExpenseItem();

        ListEntity<ExpenseItemEntity> entitiesList = api.entity().expenseitem().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().expenseitem().delete(e.getId());

        entitiesList = api.entity().expenseitem().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ExpenseItemEntity e = createSimpleExpenseItem();

        ListEntity<ExpenseItemEntity> entitiesList = api.entity().expenseitem().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().expenseitem().delete(e);

        entitiesList = api.entity().expenseitem().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(ExpenseItemEntity e, ExpenseItemEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ExpenseItemEntity e, ExpenseItemEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ExpenseItemEntity retrievedUpdatedEntity = api.entity().expenseitem().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}

