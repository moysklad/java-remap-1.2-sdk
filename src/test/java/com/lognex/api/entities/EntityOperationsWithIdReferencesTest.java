package com.lognex.api.entities;

import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntityOperationsWithIdReferencesTest extends EntityTestBase {
    @Test
    public void createWithReferenceTest() throws Exception {
        MoveDocumentEntity move = new MoveDocumentEntity();
        move.setName("move_" + randomString(3) + "_" + new Date().getTime());
        OrganizationEntity organization = simpleEntityFactory.getOwnOrganization();
        StoreEntity fromStore = simpleEntityFactory.getMainStore();
        StoreEntity toStore = simpleEntityFactory.createSimpleStore();
        move.setOrganization(new OrganizationEntity(organization.getId()));
        move.setSourceStore(new StoreEntity(fromStore.getId()));
        move.setTargetStore(new StoreEntity(toStore.getId()));

        move = api.entity().move().post(move);

        assertEquals(organization.getMeta().getHref(), move.getOrganization().getMeta().getHref());
        assertEquals(fromStore.getMeta().getHref(), move.getSourceStore().getMeta().getHref());
        assertEquals(toStore.getMeta().getHref(), move.getTargetStore().getMeta().getHref());
    }

    @Test
    public void createWithReferenceListTest() throws Exception {
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_"  + randomString(3) + "_" + new Date().getTime());
        List<InvoiceInDocumentEntity> invoicesin = new ArrayList<>();
        invoicesin.add(simpleEntityFactory.createSimpleInvoiceIn());
        invoicesin.add(simpleEntityFactory.createSimpleInvoiceIn());

        supply.setInvoicesIn(new ArrayList<>());
        for (int i = 0; i < 2; ++i) {
            supply.getInvoicesIn().add(new InvoiceInDocumentEntity(invoicesin.get(i).getId()));
        }

        OrganizationEntity organization = simpleEntityFactory.getOwnOrganization();
        StoreEntity store = simpleEntityFactory.getMainStore();
        CounterpartyEntity counterparty = simpleEntityFactory.createSimpleCounterparty();

        supply.setOrganization(new OrganizationEntity(organization.getId()));
        supply.setStore(new StoreEntity(store.getId()));
        supply.setAgent(new CounterpartyEntity(counterparty.getId()));

        api.entity().supply().post(supply);

        assertEquals(organization.getMeta().getHref(), supply.getOrganization().getMeta().getHref());
        assertEquals(store.getMeta().getHref(), supply.getStore().getMeta().getHref());
        assertEquals(2, supply.getInvoicesIn().size());
        for (int i = 0; i < 2; i++) {
            InvoiceInDocumentEntity invoiceIn = invoicesin.get(i);

            assertTrue(supply.getInvoicesIn().
                    stream().
                    anyMatch(x -> (invoiceIn.getMeta().getHref().equals(x.getMeta().getHref())))
            );
        }
    }
}
