package ru.moysklad.remap_1_2.entities.documents;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Store;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseReturnTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        purchaseReturn.setDescription(randomString());
        purchaseReturn.setVatEnabled(true);
        purchaseReturn.setVatIncluded(true);
        purchaseReturn.setMoment(LocalDateTime.now());
        Organization organization = simpleEntityManager.getOwnOrganization();
        purchaseReturn.setOrganization(organization);
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        purchaseReturn.setAgent(agent);
        Store mainStore = simpleEntityManager.getMainStore();
        purchaseReturn.setStore(mainStore);

        Supply supply = new Supply();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setOrganization(organization);
        supply.setAgent(agent);
        supply.setStore(mainStore);

        api.entity().supply().create(supply);
        purchaseReturn.setSupply(supply);

        api.entity().purchasereturn().create(purchaseReturn);

        ListEntity<PurchaseReturn> updatedEntitiesList = api.entity().purchasereturn().get(filterEq("name", purchaseReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseReturn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(purchaseReturn.getName(), retrievedEntity.getName());
        assertEquals(purchaseReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(purchaseReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(purchaseReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(purchaseReturn.getSupply().getMeta().getHref(), retrievedEntity.getSupply().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().purchasereturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().purchasereturn().metadataAttributes();
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().purchasereturn().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);

        api.entity().purchasereturn().deleteMetadataAttribute(created);

        try {
            api.entity().purchasereturn().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Test
    public void newTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = api.entity().purchasereturn().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertTrue(purchaseReturn.getVatEnabled());
        assertTrue(purchaseReturn.getVatIncluded());
        assertEquals(Long.valueOf(0), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertFalse(purchaseReturn.getPublished());
        assertFalse(purchaseReturn.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);

        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySupplyTest() throws IOException, ApiClientException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        PurchaseReturn purchaseReturn = api.entity().purchasereturn().templateDocument("supply", supply);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertEquals(supply.getVatEnabled(), purchaseReturn.getVatEnabled());
        assertEquals(supply.getVatIncluded(), purchaseReturn.getVatIncluded());
        assertEquals(supply.getPayedSum(), purchaseReturn.getPayedSum());
        assertEquals(supply.getSum(), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);
        assertEquals(supply.getMeta().getHref(), purchaseReturn.getSupply().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), purchaseReturn.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), purchaseReturn.getStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), purchaseReturn.getGroup().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), purchaseReturn.getOrganization().getMeta().getHref());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = createDefaultPurchaseReturn();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().purchasereturn().create(purchaseReturn);
        api.entity().purchasereturn().createNote(purchaseReturn.getId(), notesList.getRows().get(0));
        api.entity().purchasereturn().createNote(purchaseReturn.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().purchasereturn().getNotes(purchaseReturn.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().purchasereturn().getNotes(purchaseReturn.getId());

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
        PurchaseReturn purchaseReturn = simpleEntityManager.createSimple(PurchaseReturn.class);
        purchaseReturn.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseReturn.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseReturn.setStore(simpleEntityManager.getMainStore());

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().purchasereturn().createNote(purchaseReturn.getId(), note);

        Note retrievedNote = api.entity().purchasereturn().getNote(purchaseReturn.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = createDefaultPurchaseReturn();

        api.entity().purchasereturn().create(purchaseReturn);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().purchasereturn().createNote(purchaseReturn.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(2).getId());
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = createDefaultPurchaseReturn();

        api.entity().purchasereturn().create(purchaseReturn);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().purchasereturn().createNote(purchaseReturn.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().purchasereturn().updateNote(purchaseReturn.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().purchasereturn().updateNote(purchaseReturn.getId(), notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().purchasereturn().updateNote(purchaseReturn.getId(), notesList.getRows().get(2).getId(), updNoteByEntities);
        retrievedEntity = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().purchasereturn().updateNote(purchaseReturn.getId(), prevObject.getId(), updNoteByPrevObject);
        retrievedEntity = api.entity().purchasereturn().getNote(purchaseReturn.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = createDefaultPurchaseReturn();

        api.entity().purchasereturn().create(purchaseReturn);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().purchasereturn().createNote(purchaseReturn.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().purchasereturn().getNotes(purchaseReturn.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().purchasereturn().deleteNote(purchaseReturn.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().purchasereturn().getNotes(purchaseReturn.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().purchasereturn().deleteNote(purchaseReturn.getId(), notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().purchasereturn().getNotes(purchaseReturn.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().purchasereturn().deleteNote(purchaseReturn.getId(), notesBefore.getRows().get(2).getId());
        notesAfter = api.entity().purchasereturn().getNotes(purchaseReturn.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull PurchaseReturn createDefaultPurchaseReturn() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        purchaseReturn.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseReturn.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseReturn.setStore(simpleEntityManager.getMainStore());
        return purchaseReturn;
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PurchaseReturn originalPurchaseReturn = (PurchaseReturn) originalEntity;
        PurchaseReturn retrievedPurchaseReturn = (PurchaseReturn) retrievedEntity;

        assertEquals(originalPurchaseReturn.getName(), retrievedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), retrievedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), retrievedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), retrievedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), retrievedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PurchaseReturn originalPurchaseReturn = (PurchaseReturn) originalEntity;
        PurchaseReturn updatedPurchaseReturn = (PurchaseReturn) updatedEntity;

        assertNotEquals(originalPurchaseReturn.getName(), updatedPurchaseReturn.getName());
        assertEquals(changedField, updatedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), updatedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), updatedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), updatedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), updatedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().purchasereturn();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseReturn.class;
    }
}
