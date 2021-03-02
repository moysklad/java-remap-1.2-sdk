package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.GroupClient;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class GroupTest extends EntityTestBase {
    @Test
    public void getTest()  throws IOException, ApiClientException {
        ListEntity<Group> groupList = api.entity().group().get();
        assertEquals(1, groupList.getRows().size());
        assertEquals("Основной", groupList.getRows().get(0).getName());

        String[] split = groupList.getRows().get(0).getMeta().getHref().split("/");
        groupList.getRows().get(0).setId(split[split.length - 1]);

        Group retrievedEntity = api.entity().group().get(groupList.getRows().get(0).getId());
        getAsserts(groupList.getRows().get(0), retrievedEntity);

        retrievedEntity = api.entity().group().get(groupList.getRows().get(0));
        getAsserts(groupList.getRows().get(0), retrievedEntity);
    }

    @Test
    public void createUpdateDeleteTest()  throws IOException, ApiClientException {
        GroupClient groupClient = api.entity().group();
        Group group = new Group();
        group.setName("group_for_test");

        Group created = groupClient.create(group);
        assertEquals(group.getName(), created.getName());
        assertNotNull(created.getId());

        group.setName("updated_by_test");
        groupClient.update(group);
        Group updated = groupClient.get(created.getId());
        assertEquals(group.getName(), updated.getName());

        groupClient.delete(created.getId());
        try {
            groupClient.get(created.getId());
            fail();
        } catch (ApiClientException ex) {
            assertEquals(404, ex.getStatusCode());
        }
    }

    @Test
    public void changeOrderTest() throws IOException, ApiClientException {
        String groupName = "group_for_test";

        Group group = new Group();
        group.setName(groupName);
        group.setIndex(0);
        Group createdGroup = api.entity().group().create(group);

        List<Group> groupList = sortedGroupList();
        assertEquals(groupName, groupList.get(0).getName());
        assertNotEquals(groupName, groupList.get(1).getName());

        createdGroup.setIndex(1);
        api.entity().group().update(createdGroup);

        List<Group> updatedGroupList = sortedGroupList();
        assertNotEquals(groupName, updatedGroupList.get(0).getName());
        assertEquals(groupName, updatedGroupList.get(1).getName());

        //clean up
        api.entity().group().delete(createdGroup);
    }

    private List<Group> sortedGroupList() throws IOException, ApiClientException {
        List<Group> groups = new ArrayList<>(api.entity().group().get().getRows());
        groups.sort(Comparator.comparing(Group::getIndex));
        return groups;
    }

    private void getAsserts(Group group, Group retrievedEntity) {
        assertEquals(group.getName(), retrievedEntity.getName());
    }
}
