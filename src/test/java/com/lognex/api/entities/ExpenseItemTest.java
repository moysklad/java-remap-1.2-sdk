package com.lognex.api.entities;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ExpenseItemTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ExpenseItem expenceItem = new ExpenseItem();
        expenceItem.setName("expenseitem_" + randomString(3) + "_" + new Date().getTime());
        expenceItem.setDescription(randomString());
        expenceItem.setCode(randomString());
        expenceItem.setExternalCode(randomString());

        api.entity().expenseitem().create(expenceItem);

        ListEntity<ExpenseItem> updatedEntitiesList = api.entity().expenseitem().get(filterEq("name", expenceItem.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ExpenseItem retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(expenceItem.getName(), retrievedEntity.getName());
        assertEquals(expenceItem.getDescription(), retrievedEntity.getDescription());
        assertEquals(expenceItem.getCode(), retrievedEntity.getCode());
        assertEquals(expenceItem.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ExpenseItem originalExpenseItem = (ExpenseItem) originalEntity;
        ExpenseItem retrievedExpenseItem = (ExpenseItem) retrievedEntity;

        assertEquals(originalExpenseItem.getName(), retrievedExpenseItem.getName());
        assertEquals(originalExpenseItem.getDescription(), retrievedExpenseItem.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ExpenseItem originalExpenseItem = (ExpenseItem) originalEntity;
        ExpenseItem updatedExpenseItem = (ExpenseItem) updatedEntity;

        assertNotEquals(originalExpenseItem.getName(), updatedExpenseItem.getName());
        assertEquals(changedField, updatedExpenseItem.getName());
        assertEquals(originalExpenseItem.getDescription(), updatedExpenseItem.getDescription());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().expenseitem();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ExpenseItem.class;
    }
}

