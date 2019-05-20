package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GroupEntityTest extends EntityTestBase {
    @Test
    public void getTest()  throws IOException, LognexApiException {
        ListEntity<GroupEntity> groupList = api.entity().group().get();
        assertEquals(1, groupList.getRows().size());
        assertEquals("Основной", groupList.getRows().get(0).getName());

        String[] split = groupList.getRows().get(0).getMeta().getHref().split("/");
        groupList.getRows().get(0).setId(split[split.length - 1]);

        GroupEntity retrievedEntity = api.entity().group().get(groupList.getRows().get(0).getId());
        getAsserts(groupList.getRows().get(0), retrievedEntity);

        retrievedEntity = api.entity().group().get(groupList.getRows().get(0));
        getAsserts(groupList.getRows().get(0), retrievedEntity);
    }

    private void getAsserts(GroupEntity e, GroupEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
    }
}
