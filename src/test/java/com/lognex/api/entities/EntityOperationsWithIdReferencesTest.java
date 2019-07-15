package com.lognex.api.entities;

import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntityOperationsWithIdReferencesTest extends EntityTestBase {
    @Test
    public void createWithReferenceTest() throws Exception {
        Move move = new Move();
        move.setName("move_" + randomString(3) + "_" + new Date().getTime());
        Organization organization = simpleEntityManager.getOwnOrganization();
        Store fromStore = simpleEntityManager.getMainStore();
        Store toStore = simpleEntityManager.createSimple(Store.class);
        move.setOrganization(new Organization(organization.getId()));
        move.setSourceStore(new Store(fromStore.getId()));
        move.setTargetStore(new Store(toStore.getId()));

        move = api.entity().move().create(move);

        assertEquals(organization.getMeta().getHref(), move.getOrganization().getMeta().getHref());
        assertEquals(fromStore.getMeta().getHref(), move.getSourceStore().getMeta().getHref());
        assertEquals(toStore.getMeta().getHref(), move.getTargetStore().getMeta().getHref());
    }

    @Test
    public void createWithReferenceListTest() throws Exception {
        Supply supply = new Supply();
        supply.setName("supply_"  + randomString(3) + "_" + new Date().getTime());
        List<InvoiceIn> invoicesin = new ArrayList<>();
        invoicesin.add(simpleEntityManager.createSimpleInvoiceIn());
        invoicesin.add(simpleEntityManager.createSimpleInvoiceIn());

        supply.setInvoicesIn(new ArrayList<>());
        for (int i = 0; i < 2; ++i) {
            supply.getInvoicesIn().add(new InvoiceIn(invoicesin.get(i).getId()));
        }

        Organization organization = simpleEntityManager.getOwnOrganization();
        Store store = simpleEntityManager.getMainStore();
        Counterparty counterparty = simpleEntityManager.createSimple(Counterparty.class);

        supply.setOrganization(new Organization(organization.getId()));
        supply.setStore(new Store(store.getId()));
        supply.setAgent(new Counterparty(counterparty.getId()));

        api.entity().supply().create(supply);

        assertEquals(organization.getMeta().getHref(), supply.getOrganization().getMeta().getHref());
        assertEquals(store.getMeta().getHref(), supply.getStore().getMeta().getHref());
        assertEquals(2, supply.getInvoicesIn().size());
        for (int i = 0; i < 2; i++) {
            InvoiceIn invoiceIn = invoicesin.get(i);

            assertTrue(supply.getInvoicesIn().
                    stream().
                    anyMatch(x -> (invoiceIn.getMeta().getHref().equals(x.getMeta().getHref())))
            );
        }
    }
}
