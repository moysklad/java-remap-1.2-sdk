package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.AccountEntity;
import com.lognex.api.entities.ContactPersonEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.NoteEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CounterpartyMetadataResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public final class CounterpartyClient
        extends ApiClient
        implements
        GetListEndpoint<CounterpartyEntity>,
        PostEndpoint<CounterpartyEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<CounterpartyMetadataResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CounterpartyEntity>,
        PutByIdEndpoint<CounterpartyEntity> {

    public CounterpartyClient(LognexApi api) {
        super(api, "/entity/counterparty/");
    }

    @ApiEndpoint
    public ListEntity<AccountEntity> getAccounts(String id, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/accounts").
                apiParams(params).
                list(AccountEntity.class);
    }

    @ApiEndpoint
    public AccountEntity getAccount(String counterpartyId, String accountId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/accounts/" + accountId).
                apiParams(params).
                get(AccountEntity.class);
    }

    @ApiEndpoint
    public AccountEntity getAccount(CounterpartyEntity counterparty, String accountId, ApiParam... params) throws IOException, LognexApiException {
        return getAccount(counterparty.getId(), accountId, params);
    }

    @ApiEndpoint
    public AccountEntity getAccount(CounterpartyEntity counterparty, AccountEntity account, ApiParam... params) throws IOException, LognexApiException {
        return getAccount(counterparty, account.getId(), params);
    }

    @ApiEndpoint
    public ListEntity<ContactPersonEntity> getContactPersons(String counterpartyId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons").
                apiParams(params).
                list(ContactPersonEntity.class);
    }

    @ApiEndpoint
    public ListEntity<ContactPersonEntity> getContactPersons(CounterpartyEntity counterparty, ApiParam... params) throws IOException, LognexApiException {
        return getContactPersons(counterparty.getId(), params);
    }

    @ApiEndpoint
    public ContactPersonEntity postContactPerson(ContactPersonEntity newEntity) throws IOException, LognexApiException {
        ContactPersonEntity responseEntity = HttpRequestExecutor.
                path(api(), path() + "/contactpersons").
                body(newEntity).
                post(ContactPersonEntity.class);

        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    public ContactPersonEntity getContactPerson(String counterpartyId, String contactPersonId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId).
                apiParams(params).
                get(ContactPersonEntity.class);
    }

    @ApiEndpoint
    public ContactPersonEntity getContactPerson(CounterpartyEntity counterparty, String contactPersonId, ApiParam... params) throws IOException, LognexApiException {
        return getContactPerson(counterparty.getId(), contactPersonId, params);
    }

    @ApiEndpoint
    public ContactPersonEntity getContactPerson(CounterpartyEntity counterparty, ContactPersonEntity contactPerson, ApiParam... params) throws IOException, LognexApiException {
        return getContactPerson(counterparty, contactPerson.getId(), params);
    }

    @ApiEndpoint
    public void putContactPerson(String counterpartyId, String contactPersonId, ContactPersonEntity updatedEntity) throws IOException, LognexApiException {
        ContactPersonEntity responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/contactpersons/" + contactPersonId)
                .body(updatedEntity)
                .put(ContactPersonEntity.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void putContactPerson(CounterpartyEntity counterparty, String contactPersonId, ContactPersonEntity updatedEntity) throws IOException, LognexApiException {
        putContactPerson(counterparty.getId(), contactPersonId, updatedEntity);
    }

    @ApiEndpoint
    public void putContactPerson(CounterpartyEntity counterparty, ContactPersonEntity contactPerson, ContactPersonEntity updatedEntity) throws IOException, LognexApiException {
        putContactPerson(counterparty, contactPerson.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void putContactPerson(CounterpartyEntity counterparty, ContactPersonEntity contactPerson) throws IOException, LognexApiException {
        putContactPerson(counterparty, contactPerson.getId(), contactPerson);
    }

    @ApiEndpoint
    public ListEntity<NoteEntity> getNotes(String counterpartyId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes").
                apiParams(params).
                list(NoteEntity.class);
    }

    @ApiEndpoint
    public ListEntity<NoteEntity> getNotes(CounterpartyEntity counterparty, ApiParam... params) throws IOException, LognexApiException {
        return getNotes(counterparty.getId(), params);
    }

    @ApiEndpoint
    public NoteEntity postNote(NoteEntity newEntity) throws IOException, LognexApiException {
        NoteEntity responseEntity = HttpRequestExecutor.
                path(api(), path() + "/notes").
                body(newEntity).
                post(NoteEntity.class);

        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    public NoteEntity getNote(String counterpartyId, String noteId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                apiParams(params).
                get(NoteEntity.class);
    }

    @ApiEndpoint
    public NoteEntity getNote(CounterpartyEntity counterparty, String noteId, ApiParam... params) throws IOException, LognexApiException {
        return getNote(counterparty.getId(), noteId, params);
    }

    @ApiEndpoint
    public NoteEntity getNote(CounterpartyEntity counterparty, NoteEntity note, ApiParam... params) throws IOException, LognexApiException {
        return getNote(counterparty, note.getId(), params);
    }

    @ApiEndpoint
    public void putNote(String counterpartyId, String noteId, NoteEntity updatedEntity) throws IOException, LognexApiException {
        NoteEntity responseEntity = HttpRequestExecutor
                .path(api(), path() + counterpartyId + "/notes/" + noteId)
                .body(updatedEntity)
                .put(NoteEntity.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void putNote(CounterpartyEntity counterparty, String noteId, NoteEntity updatedEntity) throws IOException, LognexApiException {
        putNote(counterparty.getId(), noteId, updatedEntity);
    }

    @ApiEndpoint
    public void putNote(CounterpartyEntity counterparty, NoteEntity note, NoteEntity updatedEntity) throws IOException, LognexApiException {
        putNote(counterparty, note.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void putNote(CounterpartyEntity counterparty, NoteEntity note) throws IOException, LognexApiException {
        putNote(counterparty, note.getId(), note);
    }

    @ApiEndpoint
    public void deleteNote(String counterpartyId, String noteId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + counterpartyId + "/notes/" + noteId).
                delete();
    }

    @ApiEndpoint
    public void deleteNote(CounterpartyEntity counterparty, String noteId) throws IOException, LognexApiException {
        deleteNote(counterparty.getId(), noteId);
    }

    @ApiEndpoint
    public void deleteNote(CounterpartyEntity counterparty, NoteEntity note) throws IOException, LognexApiException {
        deleteNote(counterparty, note.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CounterpartyEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return CounterpartyMetadataResponse.class;
    }
}
