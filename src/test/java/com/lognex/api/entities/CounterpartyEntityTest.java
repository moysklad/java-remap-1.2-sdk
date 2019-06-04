package com.lognex.api.entities;

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

public class CounterpartyEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setArchived(false);
        e.setCompanyType(CompanyType.legal);
        e.setInn(randomString());
        e.setOgrn(randomString());

        api.entity().counterparty().post(e);

        ListEntity<CounterpartyEntity> updatedEntitiesList = api.entity().counterparty().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CounterpartyEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(e.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(e.getInn(), retrievedEntity.getInn());
        assertEquals(e.getOgrn(), retrievedEntity.getOgrn());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CounterpartyEntity e = createSimpleCounterparty();

        CounterpartyEntity retrievedEntity = api.entity().counterparty().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().counterparty().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        CounterpartyEntity e = createSimpleCounterparty();

        CounterpartyEntity retrievedOriginalEntity = api.entity().counterparty().get(e.getId());
        String name = "counterparty_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().counterparty().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "counterparty_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().counterparty().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CounterpartyEntity e = createSimpleCounterparty();

        ListEntity<CounterpartyEntity> entitiesList = api.entity().counterparty().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().counterparty().delete(e.getId());

        entitiesList = api.entity().counterparty().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CounterpartyEntity e = createSimpleCounterparty();

        ListEntity<CounterpartyEntity> entitiesList = api.entity().counterparty().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().counterparty().delete(e);

        entitiesList = api.entity().counterparty().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void getAccountTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity ae = new AccountEntity();
        ae.setIsDefault(true);
        ae.setAccountNumber(randomString());
        accounts.getRows().add(ae);
        ae.setIsDefault(false);
        accounts.getRows().add(ae);
        e.setAccounts(accounts);

        api.entity().counterparty().post(e);

        ListEntity<AccountEntity> accountList = api.entity().counterparty().getAccounts(e.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());

        AccountEntity accountById = api.entity().counterparty().getAccount(e.getId(), accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), accountById.getAccountNumber());
        assertTrue(accountById.getIsDefault());

        AccountEntity accountEntityById = api.entity().counterparty().getAccount(e, accountList.getRows().get(0).getId());
        assertEquals(accountList.getRows().get(0).getAccountNumber(), accountEntityById.getAccountNumber());
        assertTrue(accountEntityById.getIsDefault());

        AccountEntity accountEntity = api.entity().counterparty().getAccount(e, accountList.getRows().get(1));
        assertEquals(accountList.getRows().get(1).getAccountNumber(), accountEntity.getAccountNumber());
        assertFalse(accountEntity.getIsDefault());
    }

    @Test
    public void getContactPersonsTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPersonEntity> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        ContactPersonEntity contactPerson1 = new ContactPersonEntity();
        contactPerson1.setName(randomString());
        contactPersonList.getRows().add(contactPerson1);
        ContactPersonEntity contactPerson2 = new ContactPersonEntity();
        contactPerson2.setName(randomString());
        contactPersonList.getRows().add(contactPerson2);
        e.setContactpersons(contactPersonList);

        api.entity().counterparty().post(e);
        ListEntity<ContactPersonEntity> retrievedContactPersonList = api.entity().counterparty().getContactPersons(e.getId());
        assertEquals((Integer) 2, retrievedContactPersonList.getMeta().getSize());

        for (ContactPersonEntity person : retrievedContactPersonList.getRows()) {
            for (ContactPersonEntity otherPerson : contactPersonList.getRows()) {
                if (person.getId().equals(otherPerson.getId())) {
                    assertEquals(otherPerson.getName(), person.getName());
                    break;
                }
            }
        }

        ListEntity<ContactPersonEntity> contactListByEntity = api.entity().counterparty().getContactPersons(e);
        assertEquals(retrievedContactPersonList, contactListByEntity);

        ContactPersonEntity entityByIds = api.entity().counterparty().getContactPerson(e.getId(), retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByIds, retrievedContactPersonList.getRows().get(0));
        ContactPersonEntity entityByEntityId = api.entity().counterparty().getContactPerson(e, retrievedContactPersonList.getRows().get(0).getId());
        assertEquals(entityByEntityId, retrievedContactPersonList.getRows().get(0));
        ContactPersonEntity entityByEntities = api.entity().counterparty().getContactPerson(e, retrievedContactPersonList.getRows().get(1));
        assertEquals(entityByEntities, retrievedContactPersonList.getRows().get(1));
    }

    @Test
    public void postContactPersonTest() throws IOException, LognexApiException {
        CounterpartyEntity e = createSimpleCounterparty();

        ContactPersonEntity contactPerson = new ContactPersonEntity();
        contactPerson.setName(randomString());
        api.entity().counterparty().postContactPerson(e.getId(), contactPerson);

        ContactPersonEntity contactEntity = api.entity().counterparty().getContactPerson(e.getId(), contactPerson.getId());
        assertEquals(contactEntity.getName(), contactPerson.getName());
    }

    @Test
    public void putContactPersonsTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<ContactPersonEntity> contactPersonList = new ListEntity<>();
        contactPersonList.setRows(new ArrayList<>());
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            names.add(randomString());
            ContactPersonEntity contactPerson = new ContactPersonEntity();
            contactPerson.setName(randomString());
            contactPersonList.getRows().add(contactPerson);
        }
        e.setContactpersons(contactPersonList);

        api.entity().counterparty().post(e);
        ListEntity<ContactPersonEntity> contactList = api.entity().counterparty().getContactPersons(e.getId());

        ContactPersonEntity updContactByIds = new ContactPersonEntity();
        updContactByIds.setName(names.get(0));
        api.entity().counterparty().putContactPerson(e.getId(), contactList.getRows().get(0).getId(), updContactByIds);
        ContactPersonEntity retrievedEntity = api.entity().counterparty().
                getContactPerson(e.getId(), contactList.getRows().get(0).getId());
        assertNotEquals(updContactByIds.getName(), contactList.getRows().get(0).getName());
        assertEquals(updContactByIds.getName(), names.get(0));
        assertEquals(updContactByIds.getName(), retrievedEntity.getName());

        ContactPersonEntity updContactByIdEntity = new ContactPersonEntity();
        updContactByIdEntity.setName(names.get(1));
        api.entity().counterparty().putContactPerson(e, contactList.getRows().get(1).getId(), updContactByIdEntity);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(e.getId(), contactList.getRows().get(1).getId());
        assertNotEquals(updContactByIdEntity.getName(), contactList.getRows().get(1).getName());
        assertEquals(updContactByIdEntity.getName(), names.get(1));
        assertEquals(updContactByIdEntity.getName(), retrievedEntity.getName());

        ContactPersonEntity updContactByEntities = new ContactPersonEntity();
        updContactByEntities.setName(names.get(2));
        api.entity().counterparty().putContactPerson(e, contactList.getRows().get(2), updContactByEntities);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(e.getId(), contactList.getRows().get(2).getId());
        assertNotEquals(updContactByEntities.getName(), contactList.getRows().get(2).getName());
        assertEquals(updContactByEntities.getName(), names.get(2));
        assertEquals(updContactByEntities.getName(), retrievedEntity.getName());

        ContactPersonEntity updByPrevObject = new ContactPersonEntity();
        updByPrevObject.set(contactList.getRows().get(3));
        updByPrevObject.setName(names.get(3));
        api.entity().counterparty().putContactPerson(e, updByPrevObject);
        retrievedEntity = api.entity().counterparty().
                getContactPerson(e.getId(), contactList.getRows().get(3).getId());
        assertNotEquals(updByPrevObject.getName(), contactList.getRows().get(3).getName());
        assertEquals(updByPrevObject.getName(), names.get(3));
        assertEquals(updByPrevObject.getName(), retrievedEntity.getName());
    }

    @Test
    public void getNotesTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        NoteEntity note1 = new NoteEntity();
        note1.setDescription(randomString());
        notesList.getRows().add(note1);
        NoteEntity note2 = new NoteEntity();
        note2.setDescription(randomString());
        notesList.getRows().add(note2);

        api.entity().counterparty().post(e);
        api.entity().counterparty().postNote(e.getId(), notesList.getRows().get(0));
        api.entity().counterparty().postNote(e.getId(), notesList.getRows().get(1));

        ListEntity<NoteEntity> retrievedNotesById = api.entity().counterparty().getNotes(e.getId());

        assertEquals(2, retrievedNotesById.getRows().size());

        for (NoteEntity note : retrievedNotesById.getRows()) {
            for (NoteEntity otherNote : notesList.getRows()) {
                if (note.getId().equals(otherNote.getId())) {
                    assertEquals(otherNote.getName(), note.getName());
                    break;
                }
            }
        }

        ListEntity<NoteEntity> retrievedNotesByEntity = api.entity().counterparty().getNotes(e);

        assertEquals(2, retrievedNotesByEntity.getRows().size());
        assertEquals(note1.getDescription(), retrievedNotesByEntity.getRows().get(0).getDescription());
        assertEquals(note2.getDescription(), retrievedNotesByEntity.getRows().get(1).getDescription());
    }

    @Test
    public void postNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity e = createSimpleCounterparty();

        NoteEntity note = new NoteEntity();
        String name = randomString();
        note.setDescription(name);

        api.entity().counterparty().postNote(e.getId(), note);

        NoteEntity retrievedNote = api.entity().counterparty().getNote(e.getId(), note.getId());
        assertEquals(retrievedNote.getDescription(), name);
    }

    @Test
    public void getNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(e);

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            descriptions.add(randomString());
            NoteEntity note = new NoteEntity();
            note.setDescription(descriptions.get(i));
            notesList.getRows().add(note);

            api.entity().counterparty().postNote(e.getId(), notesList.getRows().get(i));
        }

        NoteEntity retrievedNoteByIds = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(0).getId());
        assertEquals(descriptions.get(0), retrievedNoteByIds.getDescription());

        NoteEntity retrievedNoteByEntityId = api.entity().counterparty().getNote(e, notesList.getRows().get(1).getId());
        assertEquals(descriptions.get(1), retrievedNoteByEntityId.getDescription());

        NoteEntity retrievedNoteByEntities = api.entity().counterparty().getNote(e, notesList.getRows().get(2));
        assertEquals(descriptions.get(2), retrievedNoteByEntities.getDescription());
    }

    @Test
    public void putNoteTest() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(e);

        ListEntity<NoteEntity> notesList = new ListEntity<>();
        notesList.setRows(new ArrayList<>());
        List<String> descriptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptions.add(randomString());
            NoteEntity note = new NoteEntity();
            note.setDescription(randomString());
            notesList.getRows().add(note);

            api.entity().counterparty().postNote(e.getId(), notesList.getRows().get(i));
        }

        NoteEntity updNoteByIds = new NoteEntity();
        updNoteByIds.setDescription(descriptions.get(0));
        api.entity().counterparty().putNote(e.getId(), notesList.getRows().get(0).getId(), updNoteByIds);
        NoteEntity retrievedEntity = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(0).getId());
        assertNotEquals(notesList.getRows().get(0).getDescription(), updNoteByIds.getDescription());
        assertEquals(descriptions.get(0), updNoteByIds.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByIds.getDescription());

        NoteEntity updNoteByEntityId = new NoteEntity();
        updNoteByEntityId.setDescription(descriptions.get(1));
        api.entity().counterparty().putNote(e, notesList.getRows().get(1).getId(), updNoteByEntityId);
        retrievedEntity = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(1).getId());
        assertNotEquals(notesList.getRows().get(1).getDescription(), updNoteByEntityId.getDescription());
        assertEquals(descriptions.get(1), updNoteByEntityId.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntityId.getDescription());

        NoteEntity updNoteByEntities = new NoteEntity();
        updNoteByEntities.setDescription(descriptions.get(2));
        api.entity().counterparty().putNote(e, notesList.getRows().get(2), updNoteByEntities);
        retrievedEntity = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(2).getId());
        assertNotEquals(notesList.getRows().get(2).getDescription(), updNoteByEntities.getDescription());
        assertEquals(descriptions.get(2), updNoteByEntities.getDescription());
        assertEquals(retrievedEntity.getDescription(), updNoteByEntities.getDescription());

        NoteEntity updNoteByPrevObject = new NoteEntity();
        NoteEntity prevObject = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(3).getId());
        updNoteByPrevObject.set(prevObject);
        updNoteByPrevObject.setDescription(descriptions.get(3));
        api.entity().counterparty().putNote(e, updNoteByPrevObject);
        retrievedEntity = api.entity().counterparty().getNote(e.getId(), notesList.getRows().get(3).getId());
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
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());

        api.entity().counterparty().post(e);

        for (int i = 0; i < 3; i++) {
            NoteEntity note = new NoteEntity();
            note.setDescription(randomString());

            api.entity().counterparty().postNote(e.getId(), note);
        }

        ListEntity<NoteEntity> notesBefore = api.entity().counterparty().getNotes(e.getId());
        assertEquals((Integer) 3, notesBefore.getMeta().getSize());

        api.entity().counterparty().deleteNote(e.getId(), notesBefore.getRows().get(0).getId());
        ListEntity<NoteEntity> notesAfter = api.entity().counterparty().getNotes(e.getId());
        assertEquals((Integer) 2, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(e, notesBefore.getRows().get(1).getId());
        notesAfter = api.entity().counterparty().getNotes(e.getId());
        assertEquals((Integer) 1, notesAfter.getMeta().getSize());

        api.entity().counterparty().deleteNote(e, notesBefore.getRows().get(2));
        notesAfter = api.entity().counterparty().getNotes(e.getId());
        assertEquals((Integer) 0, notesAfter.getMeta().getSize());
    }

    private void getAsserts(CounterpartyEntity e, CounterpartyEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(e.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(e.getInn(), retrievedEntity.getInn());
        assertEquals(e.getOgrn(), retrievedEntity.getOgrn());
        assertEquals(e.getLegalAddress(), retrievedEntity.getLegalAddress());
        assertEquals(e.getLegalTitle(), retrievedEntity.getLegalTitle());
    }

    private void putAsserts(CounterpartyEntity e, CounterpartyEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CounterpartyEntity retrievedUpdatedEntity = api.entity().counterparty().get(e.getId());

        assertNotEquals(retrievedUpdatedEntity.getName(), retrievedOriginalEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedUpdatedEntity.getCompanyType(), retrievedOriginalEntity.getCompanyType());
        assertEquals(retrievedUpdatedEntity.getAccounts(), retrievedOriginalEntity.getAccounts());
        assertEquals(retrievedUpdatedEntity.getInn(), retrievedOriginalEntity.getInn());
        assertEquals(retrievedUpdatedEntity.getOgrn(), retrievedOriginalEntity.getOgrn());
        assertEquals(retrievedUpdatedEntity.getLegalAddress(), retrievedOriginalEntity.getLegalAddress());
        assertEquals(retrievedUpdatedEntity.getLegalTitle(), retrievedOriginalEntity.getLegalTitle());
    }
}
