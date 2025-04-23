package ru.moysklad.remap_1_2.entities;

import java.time.LocalDateTime;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.CounterpartyMetadataResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CounterpartyTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());
        counterparty.setDescription(randomString());
        counterparty.setArchived(false);
        counterparty.setCompanyType(CompanyType.legal);
        counterparty.setInn(randomString());
        counterparty.setOgrn(randomString());
        counterparty.setPriceType(api.entity().companysettings().pricetype().getDefault());
        Address actualAddressFull = randomAddress(api);
        Address legalAddressFull = randomAddress(api);
        actualAddressFull.setFiasCode__ru(randomString());
        legalAddressFull.setFiasCode__ru(randomString());
        counterparty.setActualAddressFull(actualAddressFull);
        counterparty.setLegalAddressFull(legalAddressFull);

        api.entity().counterparty().create(counterparty);

        ListEntity<Counterparty> updatedEntitiesList = api.entity().counterparty().get(filterEq("name", counterparty.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Counterparty retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(counterparty.getName(), retrievedEntity.getName());
        assertEquals(counterparty.getDescription(), retrievedEntity.getDescription());
        assertEquals(counterparty.getArchived(), retrievedEntity.getArchived());
        assertEquals(counterparty.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(counterparty.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(counterparty.getInn(), retrievedEntity.getInn());
        assertEquals(counterparty.getOgrn(), retrievedEntity.getOgrn());
        assertEquals(counterparty.getPriceType(), retrievedEntity.getPriceType());
        assertAddressFull(actualAddressFull, counterparty.getActualAddressFull());
        assertAddressFull(legalAddressFull, counterparty.getLegalAddressFull());

        Counterparty counterpartyIndividual = new Counterparty();
        String male = "MALE";
        LocalDateTime birthDate = LocalDateTime.now().minusYears(1);
        counterpartyIndividual.setCompanyType(CompanyType.individual);
        counterpartyIndividual.setSex(male);
        counterpartyIndividual.setBirthDate(birthDate);
        counterpartyIndividual.setName("counterparty_" + randomString(4) + "_" + new Date().getTime());
        Counterparty individualCounterPartyForCreate = api.entity().counterparty().create(counterpartyIndividual);
        Counterparty counterpartyIndividualCreated = api.entity().counterparty().get(individualCounterPartyForCreate.getId());
        assertEquals(male, counterpartyIndividualCreated.getSex());
        assertEquals(birthDate.toLocalDate(), counterpartyIndividualCreated.getBirthDate().toLocalDate());
    }

    @Test
    public void getAccountTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AgentAccount> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AgentAccount ae = new AgentAccount();
        ae.setIsDefault(true);
        ae.setAccountNumber(randomString());
        accounts.getRows().add(ae);
        ae.setIsDefault(false);
        accounts.getRows().add(ae);
        counterparty.setAccounts(accounts);

        api.entity().counterparty().create(counterparty);

        ListEntity<AgentAccount> accountList = api.entity().counterparty().getAccounts(counterparty.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());

        AgentAccount accountById = api.entity().counterparty().getAccount(counterparty.getId(), accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), accountById.getAccountNumber());
        assertTrue(accountById.getIsDefault());

        AgentAccount agentAccountById = api.entity().counterparty().getAccount(counterparty, accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), agentAccountById.getAccountNumber());
        assertTrue(agentAccountById.getIsDefault());

        AgentAccount agentAccount = api.entity().counterparty().getAccount(counterparty, accountList.getRows().get(1));
        assertEquals(accountList.getRows().get(1).getAccountNumber(), agentAccount.getAccountNumber());
        assertFalse(agentAccount.getIsDefault());
    }

    @Test
    public void getContactPersonsTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPerson> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        ContactPerson contactPerson1 = new ContactPerson();
        contactPerson1.setName(randomString());
        contactPersonList.getRows().add(contactPerson1);
        ContactPerson contactPerson2 = new ContactPerson();
        contactPerson2.setName(randomString());
        contactPersonList.getRows().add(contactPerson2);
        counterparty.setContactpersons(contactPersonList);

        api.entity().counterparty().create(counterparty);
        ListEntity<ContactPerson> retrievedContactPersonList = api.entity().counterparty().getContactPersons(counterparty.getId());
        assertEquals((Integer) 2, retrievedContactPersonList.getMeta().getSize());

        for (ContactPerson person : retrievedContactPersonList.getRows()) {
            for (ContactPerson otherPerson : contactPersonList.getRows()) {
                if (person.getId().equals(otherPerson.getId())) {
                    assertEquals(otherPerson.getName(), person.getName());
                    break;
                }
            }
        }

        ListEntity<ContactPerson> contactListByEntity = api.entity().counterparty().getContactPersons(counterparty);
        assertEquals(retrievedContactPersonList, contactListByEntity);

        ContactPerson entityByIds = api.entity().counterparty().getContactPerson(counterparty.getId(), retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByIds, retrievedContactPersonList.getRows().get(0));
        ContactPerson entityByEntityId = api.entity().counterparty().getContactPerson(counterparty, retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByEntityId, retrievedContactPersonList.getRows().get(0));
        ContactPerson entityByEntities = api.entity().counterparty().getContactPerson(counterparty, retrievedContactPersonList.getRows().get(1));
        assertEquals(entityByEntities, retrievedContactPersonList.getRows().get(1));
    }

    @Test
    public void postContactPersonTest() throws IOException, ApiClientException {
        Counterparty counterparty = simpleEntityManager.createSimple(Counterparty.class);

        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setName(randomString());
        api.entity().counterparty().createContactPerson(counterparty.getId(), contactPerson);

        ContactPerson contactEntity = api.entity().counterparty().getContactPerson(counterparty.getId(), contactPerson.getId());
        assertEquals(contactEntity.getName(), contactPerson.getName());
    }

    @Test
    public void putContactPersonsTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPerson> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            names.add(randomString());
            ContactPerson contactPerson = new ContactPerson();
            contactPerson.setName(randomString());
            contactPersonList.getRows().add(contactPerson);
        }
        counterparty.setContactpersons(contactPersonList);

        api.entity().counterparty().create(counterparty);
        ListEntity<ContactPerson> contactList = api.entity().counterparty().getContactPersons(counterparty.getId());

        ContactPerson updContactByIds = new ContactPerson();
        updContactByIds.setName(names.get(0));
        api.entity().counterparty().updateContactPerson(counterparty.getId(), contactList.getRows().get(0).getId(), updContactByIds);
        ContactPerson retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(0).getId());
        assertNotEquals(updContactByIds.getName(), contactList.getRows().get(0).getName());
        assertEquals(updContactByIds.getName(), names.get(0));
        assertEquals(updContactByIds.getName(), retrievedEntity.getName());

        ContactPerson updContactByIdEntity = new ContactPerson();
        updContactByIdEntity.setName(names.get(1));
        api.entity().counterparty().updateContactPerson(counterparty, contactList.getRows().get(1).getId(), updContactByIdEntity);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(1).getId());
        assertNotEquals(updContactByIdEntity.getName(), contactList.getRows().get(1).getName());
        assertEquals(updContactByIdEntity.getName(), names.get(1));
        assertEquals(updContactByIdEntity.getName(), retrievedEntity.getName());

        ContactPerson updContactByEntities = new ContactPerson();
        updContactByEntities.setName(names.get(2));
        api.entity().counterparty().updateContactPerson(counterparty, contactList.getRows().get(2), updContactByEntities);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(2).getId());
        assertNotEquals(updContactByEntities.getName(), contactList.getRows().get(2).getName());
        assertEquals(updContactByEntities.getName(), names.get(2));
        assertEquals(updContactByEntities.getName(), retrievedEntity.getName());

        ContactPerson updByPrevObject = new ContactPerson();
        updByPrevObject.set(contactList.getRows().get(3));
        updByPrevObject.setName(names.get(3));
        api.entity().counterparty().updateContactPerson(counterparty, updByPrevObject);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(3).getId());
        assertNotEquals(updByPrevObject.getName(), contactList.getRows().get(3).getName());
        assertEquals(updByPrevObject.getName(), names.get(3));
        assertEquals(updByPrevObject.getName(), retrievedEntity.getName());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        Note note1 = new Note();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        Note note2 = new Note();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().counterparty().create(counterparty);
        api.entity().counterparty().createNote(counterparty.getId(), notesList.getRows().get(0));
        api.entity().counterparty().createNote(counterparty.getId(), notesList.getRows().get(1));

        ListEntity<Note> retrievedNotesById = api.entity().counterparty().getNotes(counterparty.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (Note note : retrievedNotesById.getRows()) {
            for (Note otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<Note> retrievedNotesByEntity = api.entity().counterparty().getNotes(counterparty);

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
        Counterparty counterparty = simpleEntityManager.createSimple(Counterparty.class);

        Note note = new Note();
        String name = randomString();
        note.setDescription(name);

        api.entity().counterparty().createNote(counterparty.getId(), note);

        Note retrievedNote = api.entity().counterparty().getNote(counterparty.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().create(counterparty);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().counterparty().createNote(counterparty.getId(), notesList.getRows().get(i));
        }

        Note retrievedNoteByIds = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        Note retrievedNoteByEntityId = api.entity().counterparty().getNote(counterparty, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        Note retrievedNoteByEntities = api.entity().counterparty().getNote(counterparty, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().create(counterparty);

        ListEntity<Note> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            Note note = new Note();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().counterparty().createNote(counterparty.getId(), notesList.getRows().get(i));
        }

        Note updNoteByIds = new Note();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().counterparty().updateNote(counterparty.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        Note retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        Note updNoteByEntityId = new Note();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().counterparty().updateNote(counterparty, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        Note updNoteByEntities = new Note();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().counterparty().updateNote(counterparty, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        Note updNoteByPrevObject = new Note();
        Note prevObject = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().counterparty().updateNote(counterparty, updNoteByPrevObject);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        CounterpartyMetadataResponse metadata = api.entity().counterparty().metadata();
        assertFalse(metadata.getCreateShared());
        assertNotNull(metadata.getStates());
    }

    @Test
    public void settingsTest() throws IOException, ApiClientException {
        CounterpartySettings oldSettings = api.entity().counterparty().settings().get();

        assertNotNull(oldSettings.getCreateShared());
        assertNotNull(oldSettings.getUniqueCodeRules());
        assertNotNull(oldSettings.getUniqueCodeRules().getCheckUniqueCode());
        assertNotNull(oldSettings.getUniqueCodeRules().getFillUniqueCode());

        CounterpartySettings settings = new CounterpartySettings();
        settings.setCreateShared(!oldSettings.getCreateShared());
        settings.setUniqueCodeRules(new UniqueCodeRules(!oldSettings.getUniqueCodeRules().getCheckUniqueCode(), !oldSettings.getUniqueCodeRules().getFillUniqueCode()));
        api.entity().counterparty().settings().update(settings);

        CounterpartySettings newSettings = api.entity().counterparty().settings().get();
        assertEquals(!oldSettings.getCreateShared(), newSettings.getCreateShared());
        assertEquals(!oldSettings.getUniqueCodeRules().getCheckUniqueCode(), newSettings.getUniqueCodeRules().getCheckUniqueCode());
        assertEquals(!oldSettings.getUniqueCodeRules().getFillUniqueCode(), newSettings.getUniqueCodeRules().getFillUniqueCode());
        
        //cleanup
        api.entity().counterparty().settings().update(oldSettings);
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().create(counterparty);

        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setDescription(randomString());

            api.entity().counterparty().createNote(counterparty.getId(), note);
        }

        ListEntity<Note> notesBefore = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<Note> notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty, notesBefore.getRows().get(2));
        notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().counterparty().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().counterparty().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().counterparty().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        Attribute updated = api.entity().counterparty().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().counterparty().createMetadataAttribute(attribute);

        api.entity().counterparty().deleteMetadataAttribute(created);

        try {
            api.entity().counterparty().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Counterparty originalCounterparty = (Counterparty) originalEntity;
        Counterparty retrievedCounterparty = (Counterparty) retrievedEntity;

        assertEquals(originalCounterparty.getName(), retrievedCounterparty.getName());
        assertEquals(originalCounterparty.getCompanyType(), retrievedCounterparty.getCompanyType());
        assertEquals(originalCounterparty.getAccounts(), retrievedCounterparty.getAccounts());
        assertEquals(originalCounterparty.getInn(), retrievedCounterparty.getInn());
        assertEquals(originalCounterparty.getOgrn(), retrievedCounterparty.getOgrn());
        assertEquals(originalCounterparty.getLegalAddress(), retrievedCounterparty.getLegalAddress());
        assertEquals(originalCounterparty.getLegalTitle(), retrievedCounterparty.getLegalTitle());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Counterparty updatedCounterparty = (Counterparty) updatedEntity;
        Counterparty originalCounterparty = (Counterparty) originalEntity;

        assertNotEquals(originalCounterparty.getName(), updatedCounterparty.getName());
        assertEquals(changedField, updatedCounterparty.getName());
        assertEquals(originalCounterparty.getCompanyType(), updatedCounterparty.getCompanyType());
        assertEquals(originalCounterparty.getAccounts(), updatedCounterparty.getAccounts());
        assertEquals(originalCounterparty.getInn(), updatedCounterparty.getInn());
        assertEquals(originalCounterparty.getOgrn(), updatedCounterparty.getOgrn());
        assertEquals(originalCounterparty.getLegalAddress(), updatedCounterparty.getLegalAddress());
        assertEquals(originalCounterparty.getLegalTitle(), updatedCounterparty.getLegalTitle());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().counterparty();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Counterparty.class;
    }
}
