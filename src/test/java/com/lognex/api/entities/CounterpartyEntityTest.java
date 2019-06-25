package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CounterpartyMetadataResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CounterpartyEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());
        counterparty.setDescription(randomString());
        counterparty.setArchived(false);
        counterparty.setCompanyType(CompanyType.legal);
        counterparty.setInn(randomString());
        counterparty.setOgrn(randomString());

        api.entity().counterparty().post(counterparty);

        ListEntity<CounterpartyEntity> updatedEntitiesList = api.entity().counterparty().get(filterEq("name", counterparty.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CounterpartyEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(counterparty.getName(), retrievedEntity.getName());
        assertEquals(counterparty.getDescription(), retrievedEntity.getDescription());
        assertEquals(counterparty.getArchived(), retrievedEntity.getArchived());
        assertEquals(counterparty.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(counterparty.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(counterparty.getInn(), retrievedEntity.getInn());
        assertEquals(counterparty.getOgrn(), retrievedEntity.getOgrn());
    }

    @Test
    public void getAccountTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity ae = new AccountEntity();
        ae.setIsDefault(true);
        ae.setAccountNumber(randomString());
        accounts.getRows().add(ae);
        ae.setIsDefault(false);
        accounts.getRows().add(ae);
        counterparty.setAccounts(accounts);

        api.entity().counterparty().post(counterparty);

        ListEntity<AccountEntity> accountList = api.entity().counterparty().getAccounts(counterparty.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());

        AccountEntity accountById = api.entity().counterparty().getAccount(counterparty.getId(), accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), accountById.getAccountNumber());
        assertTrue(accountById.getIsDefault());

        AccountEntity accountEntityById = api.entity().counterparty().getAccount(counterparty, accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), accountEntityById.getAccountNumber());
        assertTrue(accountEntityById.getIsDefault());

        AccountEntity accountEntity = api.entity().counterparty().getAccount(counterparty, accountList.getRows().get(1));
        assertEquals(accountList.getRows().get(1).getAccountNumber(), accountEntity.getAccountNumber());
        assertFalse(accountEntity.getIsDefault());
    }

    @Test
    public void getContactPersonsTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPersonEntity> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        ContactPersonEntity contactPerson1 = new ContactPersonEntity();
        contactPerson1.setName(randomString());
        contactPersonList.getRows().add(contactPerson1);
        ContactPersonEntity contactPerson2 = new ContactPersonEntity();
        contactPerson2.setName(randomString());
        contactPersonList.getRows().add(contactPerson2);
        counterparty.setContactpersons(contactPersonList);

        api.entity().counterparty().post(counterparty);
        ListEntity<ContactPersonEntity> retrievedContactPersonList = api.entity().counterparty().getContactPersons(counterparty.getId());
        assertEquals((Integer) 2, retrievedContactPersonList.getMeta().getSize());

        for (ContactPersonEntity person : retrievedContactPersonList.getRows()) {
            for (ContactPersonEntity otherPerson : contactPersonList.getRows()) {
                if (person.getId().equals(otherPerson.getId())) {
                    assertEquals(otherPerson.getName(), person.getName());
                    break;
                }
            }
        }

        ListEntity<ContactPersonEntity> contactListByEntity = api.entity().counterparty().getContactPersons(counterparty);
        assertEquals(retrievedContactPersonList, contactListByEntity);

        ContactPersonEntity entityByIds = api.entity().counterparty().getContactPerson(counterparty.getId(), retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByIds, retrievedContactPersonList.getRows().get(0));
        ContactPersonEntity entityByEntityId = api.entity().counterparty().getContactPerson(counterparty, retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByEntityId, retrievedContactPersonList.getRows().get(0));
        ContactPersonEntity entityByEntities = api.entity().counterparty().getContactPerson(counterparty, retrievedContactPersonList.getRows().get(1));
        assertEquals(entityByEntities, retrievedContactPersonList.getRows().get(1));
    }

    @Test
    public void postContactPersonTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = simpleEntityFactory.createSimpleCounterparty();

        ContactPersonEntity contactPerson = new ContactPersonEntity();
        contactPerson.setName(randomString());
        api.entity().counterparty().postContactPerson(counterparty.getId(), contactPerson);

        ContactPersonEntity contactEntity = api.entity().counterparty().getContactPerson(counterparty.getId(), contactPerson.getId());
        assertEquals(contactEntity.getName(), contactPerson.getName());
    }

    @Test
    public void putContactPersonsTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPersonEntity> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            names.add(randomString());
            ContactPersonEntity contactPerson = new ContactPersonEntity();
            contactPerson.setName(randomString());
            contactPersonList.getRows().add(contactPerson);
        }
        counterparty.setContactpersons(contactPersonList);

        api.entity().counterparty().post(counterparty);
        ListEntity<ContactPersonEntity> contactList = api.entity().counterparty().getContactPersons(counterparty.getId());

        ContactPersonEntity updContactByIds = new ContactPersonEntity();
        updContactByIds.setName(names.get(0));
        api.entity().counterparty().putContactPerson(counterparty.getId(), contactList.getRows().get(0).getId(), updContactByIds);
        ContactPersonEntity retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(0).getId());
        assertNotEquals(updContactByIds.getName(), contactList.getRows().get(0).getName());
        assertEquals(updContactByIds.getName(), names.get(0));
        assertEquals(updContactByIds.getName(), retrievedEntity.getName());

        ContactPersonEntity updContactByIdEntity = new ContactPersonEntity();
        updContactByIdEntity.setName(names.get(1));
        api.entity().counterparty().putContactPerson(counterparty, contactList.getRows().get(1).getId(), updContactByIdEntity);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(1).getId());
        assertNotEquals(updContactByIdEntity.getName(), contactList.getRows().get(1).getName());
        assertEquals(updContactByIdEntity.getName(), names.get(1));
        assertEquals(updContactByIdEntity.getName(), retrievedEntity.getName());

        ContactPersonEntity updContactByEntities = new ContactPersonEntity();
        updContactByEntities.setName(names.get(2));
        api.entity().counterparty().putContactPerson(counterparty, contactList.getRows().get(2), updContactByEntities);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(2).getId());
        assertNotEquals(updContactByEntities.getName(), contactList.getRows().get(2).getName());
        assertEquals(updContactByEntities.getName(), names.get(2));
        assertEquals(updContactByEntities.getName(), retrievedEntity.getName());

        ContactPersonEntity updByPrevObject = new ContactPersonEntity();
        updByPrevObject.set(contactList.getRows().get(3));
        updByPrevObject.setName(names.get(3));
        api.entity().counterparty().putContactPerson(counterparty, updByPrevObject);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(counterparty.getId(), contactList.getRows().get(3).getId());
        assertNotEquals(updByPrevObject.getName(), contactList.getRows().get(3).getName());
        assertEquals(updByPrevObject.getName(), names.get(3));
        assertEquals(updByPrevObject.getName(), retrievedEntity.getName());
    }

    @Test
    public void getNotesTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        NoteEntity note1 = new NoteEntity();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        NoteEntity note2 = new NoteEntity();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().counterparty().post(counterparty);
        api.entity().counterparty().postNote(counterparty.getId(), notesList.getRows().get(0));
        api.entity().counterparty().postNote(counterparty.getId(), notesList.getRows().get(1));

        ListEntity<NoteEntity> retrievedNotesById = api.entity().counterparty().getNotes(counterparty.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (NoteEntity note : retrievedNotesById.getRows()) {
            for (NoteEntity otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<NoteEntity> retrievedNotesByEntity = api.entity().counterparty().getNotes(counterparty);

        assertEquals(2, retrievedNotesByEntity.getRows().size());

        for (NoteEntity note : retrievedNotesByEntity.getRows()) {
            for (NoteEntity otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }
    }

    @Test
    public void postNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = simpleEntityFactory.createSimpleCounterparty();

        NoteEntity note = new NoteEntity();
        String name = randomString();
        note.setDescription(name);

        api.entity().counterparty().postNote(counterparty.getId(), note);

        NoteEntity retrievedNote = api.entity().counterparty().getNote(counterparty.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(counterparty);

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            NoteEntity note = new NoteEntity();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().counterparty().postNote(counterparty.getId(), notesList.getRows().get(i));
        }

        NoteEntity retrievedNoteByIds = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        NoteEntity retrievedNoteByEntityId = api.entity().counterparty().getNote(counterparty, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        NoteEntity retrievedNoteByEntities = api.entity().counterparty().getNote(counterparty, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(counterparty);

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            NoteEntity note = new NoteEntity();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().counterparty().postNote(counterparty.getId(), notesList.getRows().get(i));
        }

        NoteEntity updNoteByIds = new NoteEntity();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().counterparty().putNote(counterparty.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        NoteEntity retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        NoteEntity updNoteByEntityId = new NoteEntity();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().counterparty().putNote(counterparty, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        NoteEntity updNoteByEntities = new NoteEntity();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().counterparty().putNote(counterparty, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        NoteEntity updNoteByPrevObject = new NoteEntity();
        NoteEntity prevObject = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().counterparty().putNote(counterparty, updNoteByPrevObject);
        retrievedEntity = api.entity().counterparty().getNote(counterparty.getId(), notesList.getRows().get(3).getId());
        assertNotEquals(notesList.getRows().get(3).getDescription(), updNoteByPrevObject.getDescription());
        assertEquals(descriptions.get(3), updNoteByPrevObject.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByPrevObject.getDescription());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        CounterpartyMetadataResponse metadata = api.entity().counterparty().metadata();
        assertFalse(metadata.getCreateShared());
        assertEquals(5, metadata.getStates().size());
    }

    @Test
    public void deleteNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(counterparty);

        for (int i = 0; i < 3; i++) {
            NoteEntity note = new NoteEntity();
            note.setDescription(randomString());

            api.entity().counterparty().postNote(counterparty.getId(), note);
        }

        ListEntity<NoteEntity> notesBefore = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<NoteEntity> notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(counterparty, notesBefore.getRows().get(2));
        notesAfter = api.entity().counterparty().getNotes(counterparty.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CounterpartyEntity originalCounterparty = (CounterpartyEntity) originalEntity;
        CounterpartyEntity retrievedCounterparty = (CounterpartyEntity) retrievedEntity;

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
        CounterpartyEntity updatedCounterparty = (CounterpartyEntity) updatedEntity;
        CounterpartyEntity originalCounterparty = (CounterpartyEntity) originalEntity;

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
    protected ApiClient entityClient() {
        return api.entity().counterparty();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CounterpartyEntity.class;
    }
}
