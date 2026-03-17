package ru.moysklad.remap_1_2.entities.documents;

import org.checkerframework.checker.nullness.qual.NonNull;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CustomerOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        customerOrder.setDescription(randomString());
        customerOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        customerOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        ShipmentAddress shipmentAddressFull = randomShipmentAddress(api);
        customerOrder.setShipmentAddressFull(shipmentAddressFull);
        api.entity().customerorder().create(customerOrder);

        ListEntity<CustomerOrder> updatedEntitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CustomerOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(customerOrder.getName(), retrievedEntity.getName());
        assertEquals(customerOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(customerOrder.getSum(), retrievedEntity.getSum());
        assertEquals(customerOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertShipmentAddressFull(customerOrder.getShipmentAddressFull(), retrievedEntity.getShipmentAddressFull());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().customerorder().metadata().get();

        assertFalse(response.getCreateShared());
    }


    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().customerorder().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().customerorder().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);

        api.entity().customerorder().deleteMetadataAttribute(created);

        try {
            api.entity().customerorder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        CustomerOrder supply = createDefaultCustomerOrder();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().customerorder().create(supply);
        api.entity().customerorder().createNote(supply.getId(), notesList.getRows().get(0));
        api.entity().customerorder().createNote(supply.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().customerorder().getNotes(supply.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().customerorder().getNotes(supply);

        assertEquals(2, retrievedNotesByEntity.getRows().size());

        for (Note note : retrievedNotesByEntity.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }
    }

    @Test
    public void postNoteTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().customerorder().createNote(customerOrder.getId(), note);

        Note retrievedNote = api.entity().customerorder().getNote(customerOrder.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = createDefaultCustomerOrder();

        api.entity().customerorder().create(customerOrder);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().customerorder().createNote(customerOrder.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().customerorder().getNote(customerOrder, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().customerorder().getNote(customerOrder, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = createDefaultCustomerOrder();

        api.entity().customerorder().create(customerOrder);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().customerorder().createNote(customerOrder.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().customerorder().updateNote(customerOrder.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().customerorder().updateNote(customerOrder, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().customerorder().updateNote(customerOrder, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().customerorder().updateNote(customerOrder, updNoteByPrevObject);
        retrievedEntity = api.entity().customerorder().getNote(customerOrder.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = createDefaultCustomerOrder();

        api.entity().customerorder().create(customerOrder);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().customerorder().createNote(customerOrder.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().customerorder().getNotes(customerOrder.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().customerorder().deleteNote(customerOrder.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().customerorder().getNotes(customerOrder.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().customerorder().deleteNote(customerOrder, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().customerorder().getNotes(customerOrder.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().customerorder().deleteNote(customerOrder, notesBefore.getRows().get(2));
        notesAfter = api.entity().customerorder().getNotes(customerOrder.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull CustomerOrder createDefaultCustomerOrder() throws IOException, ApiClientException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        customerOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        customerOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        customerOrder.setStore(simpleEntityManager.getMainStore());
        return customerOrder;
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CustomerOrder originalCustomerOrder = (CustomerOrder) originalEntity;
        CustomerOrder retrievedCustomerOrder = (CustomerOrder) retrievedEntity;

        assertEquals(originalCustomerOrder.getName(), retrievedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), retrievedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), retrievedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), retrievedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), retrievedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), retrievedCustomerOrder.getAgent().getMeta().getHref());
        assertEquals(originalCustomerOrder.getShipmentAddress(), retrievedCustomerOrder.getShipmentAddress());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CustomerOrder originalCustomerOrder = (CustomerOrder) originalEntity;
        CustomerOrder updatedCustomerOrder = (CustomerOrder) updatedEntity;

        assertNotEquals(originalCustomerOrder.getName(), updatedCustomerOrder.getName());
        assertEquals(changedField, updatedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), updatedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), updatedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), updatedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), updatedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), updatedCustomerOrder.getAgent().getMeta().getHref());
        assertEquals(originalCustomerOrder.getShipmentAddress(), updatedCustomerOrder.getShipmentAddress());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().customerorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomerOrder.class;
    }
}
