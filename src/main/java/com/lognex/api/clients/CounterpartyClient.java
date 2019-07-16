package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.ContactPerson;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Note;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CounterpartyMetadataResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;

public final class CounterpartyClient
        extends EntityClientBase
        implements
        GetListEndpoint<Counterparty>,
        PostEndpoint<Counterparty>,
        DeleteByIdEndpoint,
        MetadataEndpoint<CounterpartyMetadataResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Counterparty>,
        PutByIdEndpoint<Counterparty> {

    public CounterpartyClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/counterparty/");
    }

    @ApiEndpoint
    public ListEntity<AgentAccount> getAccounts(String id, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/accounts").
                apiParams(params).
                list(AgentAccount.class);
    }

    @ApiEndpoint
    public AgentAccount getAccount(String counterpartyId, String accountId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/accounts/" + accountId).
                apiParams(params).
                get(AgentAccount.class);
    }

    @ApiEndpoint
    public AgentAccount getAccount(Counterparty counterparty, String accountId, ApiParam... params) throws IOException, LognexApiException {
        return getAccount(counterparty.getId(), accountId, params);
    }

    @ApiEndpoint
    public AgentAccount getAccount(Counterparty counterparty, AgentAccount account, ApiParam... params) throws IOException, LognexApiException {
        return getAccount(counterparty, account.getId(), params);
    }

    @ApiEndpoint
    public ListEntity<ContactPerson> getContactPersons(String counterpartyId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons").
                apiParams(params).
                list(ContactPerson.class);
    }

    @ApiEndpoint
    public ListEntity<ContactPerson> getContactPersons(Counterparty counterparty, ApiParam... params) throws IOException, LognexApiException {
        return getContactPersons(counterparty.getId(), params);
    }

    @ApiEndpoint
    public ContactPerson createContactPerson(String counterpartyId, ContactPerson newEntity) throws IOException, LognexApiException {
        List<ContactPerson> responseEntity = HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons").
                body(newEntity).
                postList(ContactPerson.class);

        newEntity.set(responseEntity.get(0));
        return newEntity;
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(String counterpartyId, String contactPersonId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId).
                apiParams(params).
                get(ContactPerson.class);
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(Counterparty counterparty, String contactPersonId, ApiParam... params) throws IOException, LognexApiException {
        return getContactPerson(counterparty.getId(), contactPersonId, params);
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(Counterparty counterparty, ContactPerson contactPerson, ApiParam... params) throws IOException, LognexApiException {
        return getContactPerson(counterparty, contactPerson.getId(), params);
    }

    @ApiEndpoint
    public void updateContactPerson(String counterpartyId, String contactPersonId, ContactPerson updatedEntity) throws IOException, LognexApiException {
        ContactPerson responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId)
                .body(updatedEntity)
                .put(ContactPerson.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, String contactPersonId, ContactPerson updatedEntity) throws IOException, LognexApiException {
        updateContactPerson(counterparty.getId(), contactPersonId, updatedEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, ContactPerson contactPerson, ContactPerson updatedEntity) throws IOException, LognexApiException {
        updateContactPerson(counterparty, contactPerson.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, ContactPerson contactPerson) throws IOException, LognexApiException {
        updateContactPerson(counterparty, contactPerson.getId(), contactPerson);
    }

    @ApiEndpoint
    public ListEntity<Note> getNotes(String counterpartyId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes").
                apiParams(params).
                list(Note.class);
    }

    @ApiEndpoint
    public ListEntity<Note> getNotes(Counterparty counterparty, ApiParam... params) throws IOException, LognexApiException {
        return getNotes(counterparty.getId(), params);
    }

    @ApiEndpoint
    public Note createNote(String counterpartyId, Note newEntity) throws IOException, LognexApiException {
        List<Note> responseEntity = HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes").
                body(newEntity).
                postList(Note.class);

        newEntity.set(responseEntity.get(0));
        return newEntity;
    }

    @ApiEndpoint
    public Note getNote(String counterpartyId, String noteId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                apiParams(params).
                get(Note.class);
    }

    @ApiEndpoint
    public Note getNote(Counterparty counterparty, String noteId, ApiParam... params) throws IOException, LognexApiException {
        return getNote(counterparty.getId(), noteId, params);
    }

    @ApiEndpoint
    public Note getNote(Counterparty counterparty, Note note, ApiParam... params) throws IOException, LognexApiException {
        return getNote(counterparty, note.getId(), params);
    }

    @ApiEndpoint
    public void updateNote(String counterpartyId, String noteId, Note updatedEntity) throws IOException, LognexApiException {
        Note responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/notes/" + noteId)
                .body(updatedEntity)
                .put(Note.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, String noteId, Note updatedEntity) throws IOException, LognexApiException {
        updateNote(counterparty.getId(), noteId, updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, Note note, Note updatedEntity) throws IOException, LognexApiException {
        updateNote(counterparty, note.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, Note note) throws IOException, LognexApiException {
        updateNote(counterparty, note.getId(), note);
    }

    @ApiEndpoint
    public void deleteNote(String counterpartyId, String noteId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                delete();
    }

    @ApiEndpoint
    public void deleteNote(Counterparty counterparty, String noteId) throws IOException, LognexApiException {
        deleteNote(counterparty.getId(), noteId);
    }

    @ApiEndpoint
    public void deleteNote(Counterparty counterparty, Note note) throws IOException, LognexApiException {
        deleteNote(counterparty, note.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Counterparty.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return CounterpartyMetadataResponse.class;
    }
}
