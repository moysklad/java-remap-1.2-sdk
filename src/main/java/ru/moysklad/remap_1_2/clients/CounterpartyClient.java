package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.CounterpartyMetadataResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

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
        MassCreateUpdateDeleteEndpoint<Counterparty>,
        HasStatesEndpoint,
        HasNotesEndpoint<Counterparty>,
        HasSettingsEndpoint<CounterpartySettings>,
        HasFilesEndpoint<Counterparty> {

    public CounterpartyClient(ru.moysklad.remap_1_2.ApiClient api) {
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
        return getAccount(counterparty.getId(), accountId, params);
    }

    @ApiEndpoint
    public AgentAccount getAccount(Counterparty counterparty, AgentAccount account, ApiParam... params) throws IOException, ApiClientException {
        return getAccount(counterparty, account.getId(), params);
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
        return getContactPerson(counterparty.getId(), contactPersonId, params);
    }

    @ApiEndpoint
    public ContactPerson getContactPerson(Counterparty counterparty, ContactPerson contactPerson, ApiParam... params) throws IOException, ApiClientException {
        return getContactPerson(counterparty, contactPerson.getId(), params);
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

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Counterparty.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return CounterpartyMetadataResponse.class;
    }

    @Override
    public Class<CounterpartySettings> settingsEntityClass() {
        return CounterpartySettings.class;
    }
}
