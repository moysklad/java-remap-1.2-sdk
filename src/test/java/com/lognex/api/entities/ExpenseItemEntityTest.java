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
        ExpenseItemEntity expenceItem = new ExpenseItemEntity();
        expenceItem.setName("expenseitem_" + randomString(3) + "_" + new Date().getTime());
        expenceItem.setDescription(randomString());
        expenceItem.setCode(randomString());
        expenceItem.setExternalCode(randomString());

        api.entity().expenseitem().post(expenceItem);

        ListEntity<ExpenseItemEntity> updatedEntitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ExpenseItemEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(expenceItem.getName(), retrievedEntity.getName());
        assertEquals(expenceItem.getDescription(), retrievedEntity.getDescription());
        assertEquals(expenceItem.getCode(), retrievedEntity.getCode());
        assertEquals(expenceItem.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ExpenseItemEntity expenceItem = simpleEntityFactory.createSimpleExpenseItem();

        ExpenseItemEntity retrievedEntity = api.entity().expenseitem().get(expenceItem.getId());
        getAsserts(expenceItem, retrievedEntity);

        retrievedEntity = api.entity().expenseitem().get(expenceItem);
        getAsserts(expenceItem, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ExpenseItemEntity expenceItem = simpleEntityFactory.createSimpleExpenseItem();

        ExpenseItemEntity retrievedOriginalEntity = api.entity().expenseitem().get(expenceItem.getId());
        String name = "expenseitem_" + randomString(3) + "_" + new Date().getTime();
        expenceItem.setName(name);
        api.entity().expenseitem().put(expenceItem.getId(), expenceItem);
        putAsserts(expenceItem, retrievedOriginalEntity, name);

        expenceItem = simpleEntityFactory.createSimpleExpenseItem();
        retrievedOriginalEntity = api.entity().expenseitem().get(expenceItem.getId());
        name = "expenseitem_" + randomString(3) + "_" + new Date().getTime();
        expenceItem.setName(name);
        api.entity().expenseitem().put(expenceItem);
        putAsserts(expenceItem, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ExpenseItemEntity expenceItem = simpleEntityFactory.createSimpleExpenseItem();

        ListEntity<ExpenseItemEntity> entitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().expenseitem().delete(expenceItem.getId());

        entitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ExpenseItemEntity expenceItem = simpleEntityFactory.createSimpleExpenseItem();

        ListEntity<ExpenseItemEntity> entitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().expenseitem().delete(expenceItem);

        entitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(ExpenseItemEntity expenceItem, ExpenseItemEntity retrievedEntity) {
        assertEquals(expenceItem.getName(), retrievedEntity.getName());
        assertEquals(expenceItem.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ExpenseItemEntity expenceItem, ExpenseItemEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ExpenseItemEntity retrievedUpdatedEntity = api.entity().expenseitem().get(expenceItem.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}

