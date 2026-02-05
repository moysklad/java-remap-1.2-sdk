package ru.moysklad.remap_1_2.entities.documents;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.State;
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

public class SupplyTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Supply supply = new Supply();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());
        supply.setVatEnabled(true);
        supply.setVatIncluded(true);
        supply.setMoment(LocalDateTime.now());
        supply.setOrganization(simpleEntityManager.getOwnOrganization());
        supply.setAgent(simpleEntityManager.createSimpleCounterparty());
        supply.setStore(simpleEntityManager.getMainStore());

        api.entity().supply().create(supply);

        ListEntity<Supply> updatedEntitiesList = api.entity().supply().get(filterEq("name", supply.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Supply retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(supply.getName(), retrievedEntity.getName());
        assertEquals(supply.getDescription(), retrievedEntity.getDescription());
        assertEquals(supply.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(supply.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(supply.getMoment(), retrievedEntity.getMoment());
        assertEquals(supply.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().supply().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().supply().metadataAttributes();
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().supply().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);

        api.entity().supply().deleteMetadataAttribute(created);

        try {
            api.entity().supply().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        Supply supply = api.entity().supply().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertTrue(supply.getVatEnabled());
        assertTrue(supply.getVatIncluded());
        assertEquals(Long.valueOf(0), supply.getPayedSum());
        assertEquals(Long.valueOf(0), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertFalse(supply.getPublished());
        assertFalse(supply.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);

        assertEquals(supply.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrderTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        Supply supply = api.entity().supply().templateDocument("purchaseOrder", purchaseOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(purchaseOrder.getVatEnabled(), supply.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), supply.getVatIncluded());
        assertEquals(purchaseOrder.getPayedSum(), supply.getPayedSum());
        assertEquals(purchaseOrder.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(purchaseOrder.getMeta().getHref(), supply.getPurchaseOrder().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        Supply supply = api.entity().supply().templateDocument("invoicesIn", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(invoiceIn.getVatEnabled(), supply.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), supply.getVatIncluded());
        assertEquals(invoiceIn.getPayedSum(), supply.getPayedSum());
        assertEquals(invoiceIn.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(1, supply.getInvoicesIn().size());
        assertEquals(invoiceIn.getMeta().getHref(), supply.getInvoicesIn().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().supply().states().create(state);

        List<State> retrievedStates = api.entity().supply().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        Supply supply = createDefaultSupply();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().supply().create(supply);
        api.entity().supply().createNote(supply.getId(), notesList.getRows().get(0));
        api.entity().supply().createNote(supply.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().supply().getNotes(supply.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().supply().getNotes(supply);

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
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().supply().createNote(supply.getId(), note);

        Note retrievedNote = api.entity().supply().getNote(supply.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        Supply supply = createDefaultSupply();

        api.entity().supply().create(supply);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().supply().createNote(supply.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().supply().getNote(supply, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().supply().getNote(supply, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        Supply supply = createDefaultSupply();

        api.entity().supply().create(supply);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().supply().createNote(supply.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().supply().updateNote(supply.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().supply().updateNote(supply, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().supply().updateNote(supply, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().supply().updateNote(supply, updNoteByPrevObject);
        retrievedEntity = api.entity().supply().getNote(supply.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        Supply supply = createDefaultSupply();

        api.entity().supply().create(supply);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().supply().createNote(supply.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().supply().getNotes(supply.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().supply().deleteNote(supply.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().supply().getNotes(supply.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().supply().deleteNote(supply, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().supply().getNotes(supply.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().supply().deleteNote(supply, notesBefore.getRows().get(2));
        notesAfter = api.entity().supply().getNotes(supply.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull Supply createDefaultSupply() throws IOException, ApiClientException {
        Supply supply = new Supply();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setOrganization(simpleEntityManager.getOwnOrganization());
        supply.setAgent(simpleEntityManager.createSimpleCounterparty());
        supply.setStore(simpleEntityManager.getMainStore());
        return supply;
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Supply originalSupply = (Supply) originalEntity;
        Supply retrievedSupply = (Supply) retrievedEntity;

        assertEquals(originalSupply.getName(), retrievedSupply.getName());
        assertEquals(originalSupply.getDescription(), retrievedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), retrievedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), retrievedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), retrievedSupply.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Supply originalSupply = (Supply) originalEntity;
        Supply updatedSupply = (Supply) updatedEntity;

        assertNotEquals(originalSupply.getName(), updatedSupply.getName());
        assertEquals(changedField, updatedSupply.getName());
        assertEquals(originalSupply.getDescription(), updatedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), updatedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), updatedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), updatedSupply.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().supply();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Supply.class;
    }
}
