package com.lognex.api;

import com.google.common.collect.Lists;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.base.Position;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.content.ExportTemplate;
import com.lognex.api.model.content.ExportTemplateSet;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.EmbeddedTemplate;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.model.entity.good.Service;
import com.lognex.api.model.entity.Store;
import com.lognex.api.request.RequestOption;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DocumentEndpointTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        ApiResponse response = api.entity(Type.PAYMENT_IN).list().limit(10).execute();
        assertFalse(response.hasErrors());
        assertTrue(response.getEntities().size() <= 10);
        response.getEntities().forEach(e -> assertNotNull(e.getId()));
        checkListRequest(Type.PAYMENT_IN);
    }

    @Test
    public void testReadPaymentIn() {
        ApiResponse response = api.entity(Type.PAYMENT_IN).id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0")).read().execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"));
    }

    @Test
    public void testReadPaymentInWithAgentAccountExpand() throws ConverterException {
        ApiResponse response = api.
                entity(Type.PAYMENT_IN).
                id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"))
                .read().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"));
        assertEquals(((PaymentIn) response.getEntities().get(0)).getAgentAccount().getId(), new ID("5bc8549b-9e14-11e7-7a34-5acf00403d35"));
    }

    @Test
    public void testReadPaymentsInWithAgentAccountExpand() {
        ApiResponse response = api.entity(Type.PAYMENT_IN).list().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() > 0);
        response.getEntities().stream()
                .map(o -> (PaymentIn) o)
                .filter(p -> p.getAgentAccount() != null)
                .forEach(p -> assertTrue(p.getAgentAccount().getId() != null));
    }

    @Test
    public void testReadPaymentInWithAgentExpand() {
        ApiResponse response = api.entity(Type.PAYMENT_IN).id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"))
                .read().addExpand("agent").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        assertEquals(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"), response.getEntities().get(0).getId());
        assertEquals(new ID("81c97d10-9482-11e7-7a6c-d2a9000847cc"), ((PaymentIn) response.getEntities().get(0)).getAgent().getId());
    }

    @Test
    public void testReadServices() {
        checkListRequest(Type.SERVICE);
    }

    @Test
    public void testReadCurrencies() {
        checkListRequest(Type.CURRENCY);
    }

    @Test
    public void testCreateFactureOut() {
        FactureOut factureOut = new FactureOut();
        Demand demand = new Demand();
        demand.setName(UUID.randomUUID().toString());
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().execute().getEntities().get(0);
        Counterparty agent = (Counterparty) api.entity(Type.COUNTERPARTY).list().execute().getEntities().get(0);
        Store store = (Store) api.entity(Type.STORE).list().limit(1).execute().getEntities().get(0);
        demand.setAgent(agent);
        demand.setStore(store);
        demand.setOrganization(organization);
        ApiResponse demandResponse = api.entity(Type.DEMAND).create(demand).execute();
        assertEquals(demandResponse.getStatus(), 200);
        demand = (Demand) demandResponse.getEntities().get(0);
        factureOut.getDemands().add(demand);
        ApiResponse templateResponse = api.entity(Type.FACTURE_OUT).template(factureOut).execute();
        assertEquals(templateResponse.getStatus(), 200);
        factureOut = (FactureOut) templateResponse.getEntities().get(0);
        factureOut.setApplicable(true);


        ApiResponse factureOutResponse = api.entity(Type.FACTURE_OUT).create(factureOut).execute();
        assertEquals(factureOutResponse.getStatus(), 200);
    }

    @Test
    public void testCreateFactureOutByPaymentIn() {
        FactureOut factureOut = new FactureOut();
        PaymentIn paymentIn = new PaymentIn();
        paymentIn.setName(UUID.randomUUID().toString());
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().execute().getEntities().get(0);
        Counterparty agent = (Counterparty) api.entity(Type.COUNTERPARTY).list().execute().getEntities().get(0);
        paymentIn.setAgent(agent);
        paymentIn.setOrganization(organization);
        ApiResponse demandResponse = api.entity(Type.PAYMENT_IN).create(paymentIn).execute();
        assertEquals(demandResponse.getStatus(), 200);
        paymentIn = (PaymentIn) demandResponse.getEntities().get(0);
        factureOut.getPayments().add(paymentIn);
        ApiResponse templateResponse = api.entity(Type.FACTURE_OUT).template(factureOut).execute();
        assertEquals(templateResponse.getStatus(), 200);
        factureOut = (FactureOut) templateResponse.getEntities().get(0);
        factureOut.setApplicable(true);

        ApiResponse factureOutResponse = api.entity(Type.FACTURE_OUT).create(factureOut).execute();
        assertEquals(factureOutResponse.getStatus(), 200);
        FactureOut facture = (FactureOut) factureOutResponse.getEntities().get(0);
        assertTrue(facture.getPayments().size() > 0);
    }

    @Test
    public void testReadDemands() {
        checkListRequest(Type.DEMAND);
    }

    @Test
    public void testReadDemandsWithPositions() {
        // TODO False-positive test OperationWithPositionsConverter fix in converter.
        ApiResponse response = api.entity(Type.DEMAND).list().addExpand("positions").execute();
        assertFalse(response.hasErrors());
        List<Demand> entities = (List<Demand>) response.getEntities();
        for (Demand demand : entities) {
            if (!demand.getPositions().isEmpty()) {
                for (Position p : demand.getPositions()) {
                    assertNotNull(p.getAssortment());
                }
            }
        }
    }

    @Test
    public void testCreateDemand() {
        Service service = (Service) api.entity(Type.SERVICE).list().limit(1).execute().getEntities().get(0);
        Counterparty cp = (Counterparty) api.entity(Type.COUNTERPARTY).list().limit(1).execute().getEntities().get(0);
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().limit(1).execute().getEntities().get(0);
        ApiResponse storeResponse = api.entity(Type.STORE).list().limit(1).execute();
        Store store = (Store) storeResponse.getEntities().get(0);
        Demand d = new Demand();
        d.setName(UUID.randomUUID().toString());
        d.setOrganization(organization);
        d.setAgent(cp);
        d.setStore(store);

        ShipmentOutPosition position = new ShipmentOutPosition();
        position.setAssortment(service);
        position.setQuantity(1);
        position.setPrice(1000);
        d.getPositions().add(position);
        ShipmentOutPosition position2 = new ShipmentOutPosition();
        position2.setAssortment(service);
        position2.setQuantity(2);
        position2.setPrice(150000);
        d.getPositions().add(position2);

        ApiResponse demandResp = api.entity(Type.DEMAND).create(d).execute();
        assertEquals(demandResp.getStatus(), 200);
        Demand demand = (Demand) demandResp.getEntities().get(0);
        assertEquals(demand.getAgent().getId().getValue(), cp.getId().getValue());
        assertEquals(demand.getOrganization().getId().getValue(), organization.getId().getValue());
        assertEquals(demand.getName(), demand.getName());
        assertEquals(demand.getPositionsRef().getMeta().getSize(), 2);
        assertEquals(demand.getPositionsRef().getMeta().getType(), "demandposition");
    }

    private void checkListRequest(Type type) {
        ApiResponse response = api.entity(type).list().limit(10).execute();
        assertFalse(response.hasErrors());
        List<? extends Entity> entities = response.getEntities();
        assertFalse(entities.isEmpty());
        assertTrue(entities.size() <= 10);
        entities.forEach(e -> assertNotNull(e.getId()));
    }

    @Test
    public void testCounterpartyWithAccountsExpand() {
        ApiResponse response = api.entity(Type.COUNTERPARTY).id(new ID("4fedbd4c-b26d-11e7-7a6c-d2a9002d0b23"))
                .read().addExpand("accounts").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        Counterparty counterparty = ((Counterparty) response.getEntities().get(0));
        assertFalse(counterparty.getAccounts().isEmpty());
    }

    @Ignore
    @Test
    public void testDemandTemplatesExport() {
        Demand demand = null;
        List<? extends Entity> demands = api.entity(Type.DEMAND).list().limit(1).execute().getEntities();
        if (!demands.isEmpty()) {
            demand = ((Demand) demands.get(0));
        } else {
            Service service = (Service) api.entity(Type.SERVICE).list().limit(1).execute().getEntities().get(0);
            Counterparty cp = (Counterparty) api.entity(Type.COUNTERPARTY).list().limit(1).execute().getEntities().get(0);
            Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().limit(1).execute().getEntities().get(0);
            ApiResponse storeResponse = api.entity(Type.STORE).list().limit(1).execute();
            Store store = (Store) storeResponse.getEntities().get(0);
            Demand d = new Demand();
            d.setName(UUID.randomUUID().toString());
            d.setOrganization(organization);
            d.setAgent(cp);
            d.setStore(store);

            ShipmentOutPosition position = new ShipmentOutPosition();
            position.setAssortment(service);
            position.setQuantity(1);
            position.setPrice(1000);
            d.getPositions().add(position);
            ShipmentOutPosition position2 = new ShipmentOutPosition();
            position2.setAssortment(service);
            position2.setQuantity(2);
            position2.setPrice(150000);
            d.getPositions().add(position2);

            ApiResponse response = api.entity(Type.DEMAND).create(d).execute();
            demand = (Demand) response.getEntities().get(0);
        }

        ApiResponse response = api.entity(Type.DEMAND).metadata().embeddedTemplate().execute();
        assertEquals(200, response.getStatus());
        List<EmbeddedTemplate> embeddedTemplates = (List<EmbeddedTemplate>) response.getEntities();
        assertFalse(embeddedTemplates.isEmpty());

        response = api.entity(Type.DEMAND).metadata().customTemplate().execute();
        assertEquals(200, response.getStatus());

        response = api.entity(Type.DEMAND).id(demand.getId())
                .export(new ExportTemplate(embeddedTemplates.get(0), ExportTemplate.Extension.PDF))
                .addOption(RequestOption.PRINT_DOCUMENT_CONTENT).execute();
        assertEquals(200, response.getStatus());
        try {
            File file = File.createTempFile("template", ".pdf");
            response.saveContent(file);
            assertTrue(file.exists());
            assertTrue(file.length() > 0);
            file.deleteOnExit();
        } catch (IOException e) {
        }

        response = api.entity(Type.DEMAND).id(demand.getId())
                .export(new ExportTemplateSet(Lists.newArrayList(
                        new ExportTemplateSet.TemplateWithCount(embeddedTemplates.get(0), 1),
                        new ExportTemplateSet.TemplateWithCount(embeddedTemplates.get(1), 3))))
                .addOption(RequestOption.PRINT_DOCUMENT_CONTENT).execute();
        assertEquals(200, response.getStatus());
        try {
            File file = File.createTempFile("templateSet", ".pdf");
            response.saveContent(file);
            assertTrue(file.exists());
            assertTrue(file.length() > 0);
            file.deleteOnExit();
        } catch (IOException e) {
        }
    }
}
