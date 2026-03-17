package ru.moysklad.remap_1_2.entities.documents;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setVatEnabled(true);
        purchaseOrder.setVatIncluded(true);
        purchaseOrder.setMoment(LocalDateTime.now());
        purchaseOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseOrder.setStore(simpleEntityManager.getMainStore());

        api.entity().purchaseorder().create(purchaseOrder);

        ListEntity<PurchaseOrder> updatedEntitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(purchaseOrder.getName(), retrievedEntity.getName());
        assertEquals(purchaseOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseOrder.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(purchaseOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().purchaseorder().metadataAttributes();
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().purchaseorder().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);

        api.entity().purchaseorder().deleteMetadataAttribute(created);

        try {
            api.entity().purchaseorder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertTrue(purchaseOrder.getVatEnabled());
        assertTrue(purchaseOrder.getVatIncluded());
        assertEquals(Long.valueOf(0), purchaseOrder.getPayedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getShippedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getInvoicedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getSum());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertFalse(purchaseOrder.getPublished());
        assertFalse(purchaseOrder.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);

        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, ApiClientException {
        InternalOrder internalOrder = simpleEntityManager.createSimple(InternalOrder.class);

        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument("internalOrder", internalOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertEquals(internalOrder.getSum(), purchaseOrder.getSum());
        assertEquals(internalOrder.getVatEnabled(), purchaseOrder.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), purchaseOrder.getVatIncluded());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);
        assertEquals(internalOrder.getMeta().getHref(), purchaseOrder.getInternalOrder().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), purchaseOrder.getStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), purchaseOrder.getGroup().getMeta().getHref());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), purchaseOrder.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument("customerOrders", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertEquals(customerOrder.getVatEnabled(), purchaseOrder.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), purchaseOrder.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), purchaseOrder.getPayedSum());
        assertEquals(customerOrder.getSum(), purchaseOrder.getSum());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);
        assertEquals(1, purchaseOrder.getCustomerOrders().size());
        assertEquals(customerOrder.getMeta().getHref(), purchaseOrder.getCustomerOrders().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), purchaseOrder.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), purchaseOrder.getOrganization().getMeta().getHref());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = createDefaultPurchaseOrder();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().purchaseorder().create(purchaseOrder);
        api.entity().purchaseorder().createNote(purchaseOrder.getId(), notesList.getRows().get(0));
        api.entity().purchaseorder().createNote(purchaseOrder.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().purchaseorder().getNotes(purchaseOrder.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().purchaseorder().getNotes(purchaseOrder.getId());

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
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);
        purchaseOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseOrder.setStore(simpleEntityManager.getMainStore());

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().purchaseorder().createNote(purchaseOrder.getId(), note);

        Note retrievedNote = api.entity().purchaseorder().getNote(purchaseOrder.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = createDefaultPurchaseOrder();

        api.entity().purchaseorder().create(purchaseOrder);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().purchaseorder().createNote(purchaseOrder.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(2).getId());
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = createDefaultPurchaseOrder();

        api.entity().purchaseorder().create(purchaseOrder);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().purchaseorder().createNote(purchaseOrder.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().purchaseorder().updateNote(purchaseOrder.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().purchaseorder().updateNote(purchaseOrder.getId(), notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().purchaseorder().updateNote(purchaseOrder.getId(), notesList.getRows().get(2).getId(), updNoteByEntities);
        retrievedEntity = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().purchaseorder().updateNote(purchaseOrder.getId(), prevObject.getId(), updNoteByPrevObject);
        retrievedEntity = api.entity().purchaseorder().getNote(purchaseOrder.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = createDefaultPurchaseOrder();

        api.entity().purchaseorder().create(purchaseOrder);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().purchaseorder().createNote(purchaseOrder.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().purchaseorder().getNotes(purchaseOrder.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().purchaseorder().deleteNote(purchaseOrder.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().purchaseorder().getNotes(purchaseOrder.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().purchaseorder().deleteNote(purchaseOrder.getId(), notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().purchaseorder().getNotes(purchaseOrder.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().purchaseorder().deleteNote(purchaseOrder.getId(), notesBefore.getRows().get(2).getId());
        notesAfter = api.entity().purchaseorder().getNotes(purchaseOrder.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull PurchaseOrder createDefaultPurchaseOrder() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseOrder.setStore(simpleEntityManager.getMainStore());
        return purchaseOrder;
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PurchaseOrder originalPurchaseOrder = (PurchaseOrder) originalEntity;
        PurchaseOrder retrievedPurchaseOrder = (PurchaseOrder) retrievedEntity;

        assertEquals(originalPurchaseOrder.getName(), retrievedPurchaseOrder.getName());
        assertEquals(originalPurchaseOrder.getDescription(), retrievedPurchaseOrder.getDescription());
        assertEquals(originalPurchaseOrder.getOrganization().getMeta().getHref(), retrievedPurchaseOrder.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseOrder.getAgent().getMeta().getHref(), retrievedPurchaseOrder.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PurchaseOrder originalPurchaseOrder = (PurchaseOrder) originalEntity;
        PurchaseOrder updatedPurchaseOrder = (PurchaseOrder) updatedEntity;

        assertNotEquals(originalPurchaseOrder.getName(), updatedPurchaseOrder.getName());
        assertEquals(changedField, updatedPurchaseOrder.getName());
        assertEquals(originalPurchaseOrder.getDescription(), updatedPurchaseOrder.getDescription());
        assertEquals(originalPurchaseOrder.getOrganization().getMeta().getHref(), updatedPurchaseOrder.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseOrder.getAgent().getMeta().getHref(), updatedPurchaseOrder.getAgent().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().purchaseorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseOrder.class;
    }
}
