package com.lognex.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.model.entity.Store;
import com.lognex.api.model.entity.good.Service;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HeaderTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testLognexFormatMillisecond() throws IOException {
        ID id = createDemand().getId();
        SimpleDateFormat DATETIME_FORMAT_WITH_MILLISECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat DATETIME_FORMAT_DEFAULT_NO_MILLS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ApiResponse response = api.entity(Type.DEMAND).id(id).read().execute();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getContent());
        String moment = node.get("moment").asText();
        try {
            DATETIME_FORMAT_DEFAULT_NO_MILLS.parse(moment);
        } catch (ParseException e) {
            fail();
        }
        try {
            DATETIME_FORMAT_WITH_MILLISECONDS.parse(moment);
            fail();
        } catch (ParseException e) {
        }
        response = api.entity(Type.DEMAND).id(id).read().addHeader("X-Lognex-Format-Millisecond", "true").execute();
        node = mapper.readTree(response.getContent());
        moment = node.get("moment").asText();
        try {
            DATETIME_FORMAT_WITH_MILLISECONDS.parse(moment);
        } catch (ParseException e) {
            fail();
        }
        assertTrue(moment.charAt(moment.length()-4) == '.');
    }

    @Test
    public void testPrettyPrint() {
        ID id = createDemand().getId();
        ApiResponse response = api.entity(Type.DEMAND).id(id).read().execute();
        byte[] noPrettyBytes = response.getContent();
        response = api.entity(Type.DEMAND).id(id).read().addHeader("Lognex-Pretty-Print-JSON", "true").execute();
        byte[] prettyBytes = response.getContent();
        assertTrue(prettyBytes.length > noPrettyBytes.length);
    }

    @Test
    public void testLognexPrecision() {
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
        position.setPrice(111.11111111);
        d.getPositions().add(position);
        // WITH HEADER
        ApiResponse response = api.entity(Type.DEMAND).create(d).addExpand("positions").addHeader("X-Lognex-Precision", "true").execute();
        Demand demand = (Demand) response.getEntities().get(0);
        assertEquals(111.11111111, demand.getPositions().get(0).getPrice(), 0.000000001);

        // NO HEADER
        d = new Demand();
        d.setName(UUID.randomUUID().toString());
        d.setOrganization(organization);
        d.setAgent(cp);
        d.setStore(store);

        position = new ShipmentOutPosition();
        position.setAssortment(service);
        position.setQuantity(1);
        position.setPrice(111.11111111);
        d.getPositions().add(position);
        response = api.entity(Type.DEMAND).create(d).addExpand("positions").execute();
        demand = (Demand) response.getEntities().get(0);
        assertEquals(111, demand.getPositions().get(0).getPrice(), 0.000000001);
    }


    // Протестировать вебхуковый заголовок не представляется возможным
    private Demand createDemand(){
        Demand demand = new Demand();
        demand.setName(UUID.randomUUID().toString());
        demand.setMoment(new Date());
        Organization organization = (Organization) api.entity(Type.ORGANIZATION).list().execute().getEntities().get(0);
        Counterparty agent = (Counterparty) api.entity(Type.COUNTERPARTY).list().execute().getEntities().get(0);
        Store store = (Store) api.entity(Type.STORE).list().limit(1).execute().getEntities().get(0);
        demand.setAgent(agent);
        demand.setStore(store);
        demand.setOrganization(organization);
        ApiResponse demandResponse = api.entity(Type.DEMAND).create(demand).execute();
        return (Demand) demandResponse.getEntities().get(0);
    }

}
