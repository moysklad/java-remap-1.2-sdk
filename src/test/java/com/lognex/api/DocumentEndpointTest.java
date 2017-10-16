package com.lognex.api;

import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.model.base.Position;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.model.entity.Service;
import com.lognex.api.model.entity.Store;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DocumentEndpointTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        checkListRequest(Type.PAYMENT_IN);
    }

    @Test
    public void testReadPaymentIn() throws Exception{
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
        assertEquals(((PaymentIn)response.getEntities().get(0)).getAgentAccount().getId(), new ID("5bc8549b-9e14-11e7-7a34-5acf00403d35"));
    }

    @Test
    public void testReadPaymentsInWithAgentAccountExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENT_IN).list().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() > 0);
        response.getEntities().stream()
                .map(o -> (PaymentIn)o)
                .filter(p -> p.getAgentAccount() != null)
                .forEach(p -> assertTrue(p.getAgentAccount().getId() != null));
    }

    @Test
    public void testReadPaymentInWithAgentExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENT_IN).id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"))
                .read().addExpand("agent").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        assertEquals(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"), response.getEntities().get(0).getId());
        assertEquals(new ID("81c97d10-9482-11e7-7a6c-d2a9000847cc"), ((PaymentIn)response.getEntities().get(0)).getAgent().getId());
    }

    @Test
    public void testReadServices() throws Exception {
        checkListRequest(Type.SERVICE);
    }

    @Test
    public void testReadCurrencies() throws Exception {
        checkListRequest(Type.CURRENCY);
    }


    @Test
    public void testCreateFactureOut() throws Exception {
        FactureOut factureOut = new FactureOut();
        Demand demand = new Demand();
        demand.setName("211s7232s");
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().execute().getEntities().get(0);
        Counterparty agent = (Counterparty) api.entity(Type.COUNTERPARTY).list().execute().getEntities().get(0);
        Store store = (Store) api.entity(Type.STORE).list().limit(1).execute().getEntities().get(0);
        demand.setAgent(agent);
        demand.setStore(store);
        demand.setOrganization(organization);
        ApiResponse demandResponse =  api.entity(Type.DEMAND).create(demand).execute();
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
    public void testReadDemands() throws Exception {
        checkListRequest(Type.DEMAND);
    }

    @Test
    public void testReadDemandsWithPositions() throws Exception {
        // TODO False-positive test AbstractOperationWithPositionsConverter fix in converter.
        ApiResponse response = api.entity(Type.DEMAND).list().addExpand("positions").execute();
        assertFalse(response.hasErrors());
        List<Demand> entities = (List<Demand>) response.getEntities();
        for (Demand demand : entities){
            if (!demand.getPositions().isEmpty()){
                for (Position p : demand.getPositions()){
                    assertNotNull(p.getAssortment());
                }
            }
        }
    }

    @Test
    public void testCreateDemand() throws Exception {
        Service service = (Service) api.entity(Type.SERVICE).list().limit(1).execute().getEntities().get(0);
        Counterparty cp = (Counterparty) api.entity(Type.COUNTERPARTY).list().limit(1).execute().getEntities().get(0);
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().limit(1).execute().getEntities().get(0);
        ApiResponse storeResponse = api.entity(Type.STORE).list().limit(1).execute();
        Store store = (Store) storeResponse.getEntities().get(0);
        Demand d = new Demand();
        d.setName("szname323s5zzsx");
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
        List<? extends AbstractEntity> entities = response.getEntities();
        assertFalse(entities.isEmpty());
        assertTrue(entities.size() <= 10);
        entities.forEach(e -> assertNotNull(e.getId()));
    }

    @Test
    public void testCounterpartyWithAccountsExpand() throws Exception {
        ApiResponse response = api.entity(Type.COUNTERPARTY).id(new ID("4fedbd4c-b26d-11e7-7a6c-d2a9002d0b23"))
                .read().addExpand("accounts").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        Counterparty counterparty = ((Counterparty) response.getEntities().get(0));
        assertFalse(counterparty.getAccounts().isEmpty());
    }
}
