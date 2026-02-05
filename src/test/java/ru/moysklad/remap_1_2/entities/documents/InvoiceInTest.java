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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceInTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = new InvoiceIn();
        invoiceIn.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);
        invoiceIn.setMoment(LocalDateTime.now());
        invoiceIn.setSum(randomLong(10, 10000));
        invoiceIn.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceIn.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceIn.setStore(simpleEntityManager.getMainStore());
        api.entity().invoicein().create(invoiceIn);

        ListEntity<InvoiceIn> updatedEntitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceIn.getName(), retrievedEntity.getName());
        assertEquals(invoiceIn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceIn.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().invoicein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().commissionreportin().metadataAttributes();
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().commissionreportin().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        api.entity().commissionreportin().deleteMetadataAttribute(created);

        try {
            api.entity().commissionreportin().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().invoicein().states().create(state);

        List<State> retrievedStates = api.entity().invoicein().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = createDefaultInvoiceIn();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().invoicein().create(invoiceIn);
        api.entity().invoicein().createNote(invoiceIn.getId(), notesList.getRows().get(0));
        api.entity().invoicein().createNote(invoiceIn.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().invoicein().getNotes(invoiceIn.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().invoicein().getNotes(invoiceIn);

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
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().invoicein().createNote(invoiceIn.getId(), note);

        Note retrievedNote = api.entity().invoicein().getNote(invoiceIn.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = createDefaultInvoiceIn();

        api.entity().invoicein().create(invoiceIn);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().invoicein().createNote(invoiceIn.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().invoicein().getNote(invoiceIn, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().invoicein().getNote(invoiceIn, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = createDefaultInvoiceIn();

        api.entity().invoicein().create(invoiceIn);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().invoicein().createNote(invoiceIn.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().invoicein().updateNote(invoiceIn.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().invoicein().updateNote(invoiceIn, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().invoicein().updateNote(invoiceIn, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().invoicein().updateNote(invoiceIn, updNoteByPrevObject);
        retrievedEntity = api.entity().invoicein().getNote(invoiceIn.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = createDefaultInvoiceIn();

        api.entity().invoicein().create(invoiceIn);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().invoicein().createNote(invoiceIn.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().invoicein().getNotes(invoiceIn.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().invoicein().deleteNote(invoiceIn.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().invoicein().getNotes(invoiceIn.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().invoicein().deleteNote(invoiceIn, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().invoicein().getNotes(invoiceIn.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().invoicein().deleteNote(invoiceIn, notesBefore.getRows().get(2));
        notesAfter = api.entity().invoicein().getNotes(invoiceIn.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull InvoiceIn createDefaultInvoiceIn() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = new InvoiceIn();
        invoiceIn.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceIn.setAgent(simpleEntityManager.createSimpleCounterparty());
        return invoiceIn;
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn retrievedInvoiceIn = (InvoiceIn) retrievedEntity;

        assertEquals(originalInvoiceIn.getName(), retrievedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), retrievedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), retrievedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn updatedInvoiceIn = (InvoiceIn) updatedEntity;

        assertNotEquals(originalInvoiceIn.getName(), updatedInvoiceIn.getName());
        assertEquals(changedField, updatedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), updatedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), updatedInvoiceIn.getAgent().getMeta().getHref());
    }


    @Override
    public EntityClientBase entityClient() {
        return api.entity().invoicein();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceIn.class;
    }
}
