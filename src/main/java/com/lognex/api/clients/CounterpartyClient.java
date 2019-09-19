package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AgentAccount;
import com.lognex.api.entities.ContactPerson;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Note;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CounterpartyMetadataResponse;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
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
        PutByIdEndpoint<Counterparty>,
        HasStatesEndpoint {

    public CounterpartyClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/counterparty/");
    }

    @ApiEndpoint
    public ListEntity<AgentAccount> getAccounts(String id, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/accounts").
                apiParams(params).
                list(AgentAccount.class);
    }

    @ApiEndpoint
    public AgentAccount getAccount(String counterpartyId, String accountId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/accounts/" + accountId).
                apiParams(params).
                get(AgentAccount.class);
    }

    @ApiEndpoint
    public AgentAccount getAccount(Counterparty counterparty, String accountId, ApiParam... params) throws IOException, ApiClientException {
        return getAccount(counterparty.getId(), accountId);
    }

    @ApiEndpoint
    public AgentAccount getAccount(Counterparty counterparty, AgentAccount account, ApiParam... params) throws IOException, ApiClientException {
        return getAccount(counterparty, account.getId());
    }

    @ApiEndpoint
    public ListEntity<ContactPerson> getContactPersons(String counterpartyId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons").
                apiParams(params).
                list(ContactPerson.class);
    }

    @ApiEndpoint
    public ListEntity<ContactPerson> getContactPersons(Counterparty counterparty, ApiParam... params) throws IOException, ApiClientException {
        return getContactPersons(counterparty.getId(), params);
    }

    @ApiEndpoint
    public ContactPerson createContactPerson(String counterpartyId, ContactPerson newEntity) throws IOException, ApiClientException {
        List<ContactPerson> responseEntity = HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons").
                body(newEntity).
                postList(ContactPerson.class);

        newEntity.set(responseEntity.get(0));
        return newEntity;
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(String counterpartyId, String contactPersonId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId).
                apiParams(params).
                get(ContactPerson.class);
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(Counterparty counterparty, String contactPersonId, ApiParam... params) throws IOException, ApiClientException {
        return getContactPerson(counterparty.getId(), contactPersonId);
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(Counterparty counterparty, ContactPerson contactPerson, ApiParam... params) throws IOException, ApiClientException {
        return getContactPerson(counterparty, contactPerson.getId());
    }

    @ApiEndpoint
    public void updateContactPerson(String counterpartyId, String contactPersonId, ContactPerson updatedEntity) throws IOException, ApiClientException {
        ContactPerson responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId)
                .body(updatedEntity)
                .put(ContactPerson.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, String contactPersonId, ContactPerson updatedEntity) throws IOException, ApiClientException {
        updateContactPerson(counterparty.getId(), contactPersonId, updatedEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, ContactPerson contactPerson, ContactPerson updatedEntity) throws IOException, ApiClientException {
        updateContactPerson(counterparty, contactPerson.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void updateContactPerson(Counterparty counterparty, ContactPerson contactPerson) throws IOException, ApiClientException {
        updateContactPerson(counterparty, contactPerson.getId(), contactPerson);
    }

    @ApiEndpoint
    public ListEntity<Note> getNotes(String counterpartyId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes").
                apiParams(params).
                list(Note.class);
    }

    @ApiEndpoint
    public ListEntity<Note> getNotes(Counterparty counterparty, ApiParam... params) throws IOException, ApiClientException {
        return getNotes(counterparty.getId(), params);
    }

    @ApiEndpoint
    public Note createNote(String counterpartyId, Note newEntity) throws IOException, ApiClientException {
        List<Note> responseEntity = HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes").
                body(newEntity).
                postList(Note.class);

        newEntity.set(responseEntity.get(0));
        return newEntity;
    }

    @ApiEndpoint
    public Note getNote(String counterpartyId, String noteId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                get(Note.class);
    }

    @ApiEndpoint
    public Note getNote(Counterparty counterparty, String noteId) throws IOException, ApiClientException {
        return getNote(counterparty.getId(), noteId);
    }

    @ApiEndpoint
    public Note getNote(Counterparty counterparty, Note note) throws IOException, ApiClientException {
        return getNote(counterparty, note.getId());
    }

    @ApiEndpoint
    public void updateNote(String counterpartyId, String noteId, Note updatedEntity) throws IOException, ApiClientException {
        Note responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/notes/" + noteId)
                .body(updatedEntity)
                .put(Note.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, String noteId, Note updatedEntity) throws IOException, ApiClientException {
        updateNote(counterparty.getId(), noteId, updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, Note note, Note updatedEntity) throws IOException, ApiClientException {
        updateNote(counterparty, note.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Counterparty counterparty, Note note) throws IOException, ApiClientException {
        updateNote(counterparty, note.getId(), note);
    }

    @ApiEndpoint
    public void deleteNote(String counterpartyId, String noteId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                delete();
    }

    @ApiEndpoint
    public void deleteNote(Counterparty counterparty, String noteId) throws IOException, ApiClientException {
        deleteNote(counterparty.getId(), noteId);
    }

    @ApiEndpoint
    public void deleteNote(Counterparty counterparty, Note note) throws IOException, ApiClientException {
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
