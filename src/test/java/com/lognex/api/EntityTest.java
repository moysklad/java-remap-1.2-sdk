package com.lognex.api;

import com.lognex.api.converter.field.CompanyType;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

public class EntityTest {

    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"));

    @Test
    public void testCreateAndGetCounterparty() throws Exception{
        Counterparty cp = new Counterparty();
        cp.setName("Володя LOL PRO2");
        ApiResponse response1 =  api.entity("counterparty").create(cp).execute();
        assertTrue(response1.getStatus() == 200);
        assertFalse(response1.hasErrors());
        ID id = response1.getEntities().get(0).getId();

        ApiResponse response = api.entity("counterparty").id(id).read().execute();
        assertFalse(response.hasErrors());
        Counterparty counterparty = (Counterparty) response.getEntities().get(0);
        assertNotNull(counterparty.getId());
        assertEquals(counterparty.getName(), "Володя LOL PRO2");
    }

    @Test
    public void testCounterpartyWithAdditionaFields() throws Exception{
        ApiResponse response = api.entity("counterparty").id(new ID("b9dcaab9-adba-11e7-6b01-4b1d003d6037")).read().execute();
        Counterparty cp  = (Counterparty) response.getEntities().get(0);
        assertTrue(cp.getAttributes().size() > 0);
    }

    @Test
    public void testCounterpartyWithRequisiteFields() throws Exception{
        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");

        ApiResponse response = api.entity("counterparty").create(counterparty).execute();
        assertTrue(response.getStatus() == 200);
        assertFalse(response.hasErrors());

        Counterparty created = (Counterparty) response.getEntities().get(0);
        assertEquals(created.getName(), counterparty.getName());
        assertEquals(created.getCompanyType(), counterparty.getCompanyType());
        assertEquals(created.getLegalTitle(), counterparty.getLegalTitle());
        assertEquals(created.getInn(), counterparty.getInn());
        assertEquals(created.getKpp(), counterparty.getKpp());
        assertEquals(created.getOgrn(), counterparty.getOgrn());
        assertEquals(created.getOkpo(), counterparty.getOkpo());
    }


}
