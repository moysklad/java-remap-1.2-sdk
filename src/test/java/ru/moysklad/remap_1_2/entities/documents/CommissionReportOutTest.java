package ru.moysklad.remap_1_2.entities.documents;

import org.checkerframework.checker.nullness.qual.NonNull;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CommissionReportOutTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = new CommissionReportOut();
        commissionReportOut.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        commissionReportOut.setDescription(randomString());
        commissionReportOut.setVatEnabled(true);
        commissionReportOut.setVatIncluded(true);
        commissionReportOut.setMoment(LocalDateTime.now());
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        commissionReportOut.setAgent(agent);
        Organization organization = simpleEntityManager.getOwnOrganization();
        commissionReportOut.setOrganization(organization);

        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(organization);
        contract.setAgent(agent);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportOut.setContract(contract);

        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().create(commissionReportOut);

        ListEntity<CommissionReportOut> updatedEntitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(commissionReportOut.getName(), retrievedEntity.getName());
        assertEquals(commissionReportOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(commissionReportOut.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(commissionReportOut.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(commissionReportOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().commissionreportout().metadataAttributes();
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
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().commissionreportout().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);

        api.entity().commissionreportout().deleteMetadataAttribute(created);

        try {
            api.entity().commissionreportout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = createDefaultCommissionReportOut();

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().commissionreportout().create(commissionReportOut);
        api.entity().commissionreportout().createNote(commissionReportOut.getId(), notesList.getRows().get(0));
        api.entity().commissionreportout().createNote(commissionReportOut.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().commissionreportout().getNotes(commissionReportOut.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().commissionreportout().getNotes(commissionReportOut);

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
        CommissionReportOut commissionReportOut = simpleEntityManager.createSimple(CommissionReportOut.class);

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().commissionreportout().createNote(commissionReportOut.getId(), note);

        Note retrievedNote = api.entity().commissionreportout().getNote(commissionReportOut.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = createDefaultCommissionReportOut();

        api.entity().commissionreportout().create(commissionReportOut);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().commissionreportout().createNote(commissionReportOut.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().commissionreportout().getNote(commissionReportOut, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().commissionreportout().getNote(commissionReportOut, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = createDefaultCommissionReportOut();

        api.entity().commissionreportout().create(commissionReportOut);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().commissionreportout().createNote(commissionReportOut.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().commissionreportout().updateNote(commissionReportOut.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().commissionreportout().updateNote(commissionReportOut, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().commissionreportout().updateNote(commissionReportOut, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().commissionreportout().updateNote(commissionReportOut, updNoteByPrevObject);
        retrievedEntity = api.entity().commissionreportout().getNote(commissionReportOut.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = createDefaultCommissionReportOut();

        api.entity().commissionreportout().create(commissionReportOut);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().commissionreportout().createNote(commissionReportOut.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().commissionreportout().getNotes(commissionReportOut.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().commissionreportout().deleteNote(commissionReportOut.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().commissionreportout().getNotes(commissionReportOut.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().commissionreportout().deleteNote(commissionReportOut, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().commissionreportout().getNotes(commissionReportOut.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().commissionreportout().deleteNote(commissionReportOut, notesBefore.getRows().get(2));
        notesAfter = api.entity().commissionreportout().getNotes(commissionReportOut.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private @NonNull CommissionReportOut createDefaultCommissionReportOut() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = new CommissionReportOut();
        commissionReportOut.setName("commissionReportOut_" + randomString(3) + "_" + new Date().getTime());
        Organization ownOrganization = simpleEntityManager.getOwnOrganization();
        commissionReportOut.setOrganization(ownOrganization);
        Counterparty simpleCounterparty = simpleEntityManager.createSimpleCounterparty();
        commissionReportOut.setAgent(simpleCounterparty);
        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));
        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(simpleCounterparty);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportOut.setContract(contract);
        return commissionReportOut;
    }



    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CommissionReportOut originalCommissionReportOut = (CommissionReportOut) originalEntity;
        CommissionReportOut retrievedCommissionReportOut = (CommissionReportOut) retrievedEntity;

        assertEquals(originalCommissionReportOut.getName(), retrievedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), retrievedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), retrievedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), retrievedCommissionReportOut.getContract().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CommissionReportOut originalCommissionReportOut = (CommissionReportOut) originalEntity;
        CommissionReportOut updatedCommissionReportOut = (CommissionReportOut) updatedEntity;

        assertNotEquals(originalCommissionReportOut.getName(), updatedCommissionReportOut.getName());
        assertEquals(changedField, updatedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), updatedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), updatedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), updatedCommissionReportOut.getContract().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().commissionreportout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportOut.class;
    }
}
