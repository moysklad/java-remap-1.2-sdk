package com.lognex.api;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.base.field.CompanyType;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.CustomEntity;
import com.lognex.api.model.entity.State;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.attribute.AttributeType;
import com.lognex.api.model.entity.attribute.AttributeValue;
import com.lognex.api.request.filter.FilterOperator;
import com.lognex.api.request.sort.Sort;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityTest {

    private static final String COUNTERPARTY_CONVERSTAION_STATE = "e7737531-93ce-11e7-7a34-5acf001a6247";
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testCreateCounterpartyWithState() throws Exception {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("WoolStore");
        counterparty.setEmail("woolstore@woolstore.com");
        AgentAccount agentAccount = new AgentAccount();
        agentAccount.setAccountNumber("41241421421");
        agentAccount.setBankLocation("NY");
        agentAccount.setBankName("YN");
        agentAccount.setBic("1231412");
        agentAccount.setCorrespondentAccount("23523532523");
        agentAccount.setDefault(true);
        counterparty.getAccounts().add(agentAccount);
        counterparty.getTags().add("u3");

        State state = new State();
        state.setId(new ID(COUNTERPARTY_CONVERSTAION_STATE));
        counterparty.setState(state);
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(counterparty).execute();
        assertEquals(response.getStatus(), 200);
        assertFalse(response.hasErrors());
        Counterparty actualCounterparty = (Counterparty) response.getEntities().get(0);
        assertEquals(COUNTERPARTY_CONVERSTAION_STATE, actualCounterparty.getState().getId().getValue());
    }

    @Test
    public void testCreateAndGetCounterparty() throws Exception {

        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");
        counterparty.getAttributes().add(new Attribute<>("af686e1c-adba-11e7-7a34-5acf003d7f2e", AttributeType.STRING, new AttributeValue<>("string")));
        counterparty.getAttributes().add(new Attribute<>("af687111-adba-11e7-7a34-5acf003d7f2f", AttributeType.LONG, new AttributeValue<>(100L)));
        // Потому что наш API обрезает секунды при проставлении значения в доп. поле =_=
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        counterparty.getAttributes().add(new Attribute<>("af6872f9-adba-11e7-7a34-5acf003d7f30", AttributeType.TIME, new AttributeValue<>(calendar.getTime())));

        Counterparty cp1 = new Counterparty();
        cp1.setName("Петюня братюня");
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(cp1).execute();
        cp1 = (Counterparty) response.getEntities().get(0);
        counterparty.getAttributes().add(new Attribute<>("af6874ef-adba-11e7-7a34-5acf003d7f31", AttributeType.COUNTERPARTY, new AttributeValue<>(cp1)));


        counterparty.getAttributes().add(new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", AttributeType.DOUBLE, new AttributeValue<>(20.00)));
        counterparty.getAttributes().add(new Attribute<>("af687e1c-adba-11e7-7a34-5acf003d7f34", AttributeType.BOOLEAN, new AttributeValue<>(true)));
        counterparty.getAttributes().add(new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", AttributeType.TEXT, new AttributeValue<>("text attribute")));
        counterparty.getAttributes().add(new Attribute<>("af688152-adba-11e7-7a34-5acf003d7f36", AttributeType.LINK, new AttributeValue<>("link.link.link")));

        CustomEntity customEntity = new CustomEntity();
        customEntity.setName("must be ensured");

        counterparty.getAttributes().add(new Attribute<>("37caf134-adbe-11e7-6b01-4b1d003e24b7", AttributeType.CUSTOMENTITY, new AttributeValue<>(customEntity)));


        response = api.entity(Type.COUNTERPARTY).create(counterparty).execute();
        assertEquals(response.getStatus(), 200);
        assertFalse(response.hasErrors());

        Counterparty created = (Counterparty) response.getEntities().get(0);
        assertEquals(created.getName(), counterparty.getName());
        assertEquals(created.getCompanyType(), counterparty.getCompanyType());
        assertEquals(created.getLegalTitle(), counterparty.getLegalTitle());
        assertEquals(created.getInn(), counterparty.getInn());
        assertEquals(created.getKpp(), counterparty.getKpp());
        assertEquals(created.getOgrn(), counterparty.getOgrn());
        assertEquals(created.getOkpo(), counterparty.getOkpo());
        checkAttributesEquality(created, counterparty);
    }

    @Test
    public void testCreateCounterpartyWithAccountsAndTags() throws Exception {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("WoolStore");
        counterparty.setEmail("woolstore@woolstore.com");
        AgentAccount agentAccount = new AgentAccount();
        agentAccount.setAccountNumber("41241421421");
        agentAccount.setBankLocation("NY");
        agentAccount.setBankName("YN");
        agentAccount.setBic("1231412");
        agentAccount.setCorrespondentAccount("23523532523");
        agentAccount.setDefault(true);
        counterparty.getAccounts().add(agentAccount);
        counterparty.getTags().add("u3");

        ApiResponse response = api.entity(Type.COUNTERPARTY).create(counterparty).execute();
        ID id = response.getEntities().get(0).getId();
        response = api.entity(Type.COUNTERPARTY).id(id)
                .read().addExpand("accounts").execute();
        Counterparty result = ((Counterparty) response.getEntities().get(0));
        assertEquals(counterparty.getName(), result.getName());
        assertEquals(counterparty.getEmail(), result.getEmail());
        assertEquals(counterparty.getAccounts().size(), result.getAccounts().size());
        AgentAccount agentAccount1 = result.getAccounts().get(0);
        assertEquals(agentAccount.getAccountNumber(), agentAccount1.getAccountNumber());
        assertEquals(agentAccount.getBankLocation(), agentAccount1.getBankLocation());
        assertEquals(agentAccount.getBankName(), agentAccount1.getBankName());
        assertEquals(agentAccount.getBic(), agentAccount1.getBic());
        assertEquals(agentAccount.getCorrespondentAccount(), agentAccount1.getCorrespondentAccount());
        assertTrue(agentAccount1.isDefault());
        assertEquals(counterparty.getTags().size(), result.getTags().size());
        assertEquals(counterparty.getTags().get(0), result.getTags().get(0));
    }

    @Test
    public void testCounterpartySorted() throws Exception {
        String demoName = "a" + UUID.randomUUID().toString();
        Counterparty demo1 = new Counterparty();
        demo1.setName(demoName);
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(demo1).execute();
        assertEquals(200, response.getStatus());


        String demo2Name = "b" + UUID.randomUUID().toString();
        Counterparty demo2 = new Counterparty();
        demo2.setName(demo2Name);
        response = api.entity(Type.COUNTERPARTY).create(demo2).execute();
        assertEquals(200, response.getStatus());


        response = api
                .entity(Type.COUNTERPARTY)
                .list()
                // start filter
                .buildFilter()
                .filter("name", FilterOperator.EQUALS, demoName)
                .filter("name", FilterOperator.EQUALS, demo2Name)
                .build()
                // end filter
                .sorts(new Sort("name", Sort.Direction.DESC))
                .limit(5)
                .execute();

        assertEquals(200, response.getStatus());
        List<Counterparty> counterparties = (List<Counterparty>) response.getEntities();
        assertEquals(2, counterparties.size());
        assertEquals(demo2Name, counterparties.get(0).getName());
        assertEquals(demoName, counterparties.get(1).getName());

        response = api
                .entity(Type.COUNTERPARTY)
                .list()
                // start filter
                .buildFilter()
                .filter("name", FilterOperator.EQUALS, demoName)
                .filter("name", FilterOperator.EQUALS, demo2Name)
                .build()
                // end filter
                .sorts(new Sort("name", Sort.Direction.ASC))
                .limit(5)
                .execute();


        assertEquals(200, response.getStatus());
        counterparties = (List<Counterparty>) response.getEntities();
        assertEquals(2, counterparties.size());
        assertEquals(demoName, counterparties.get(0).getName());
        assertEquals(demo2Name, counterparties.get(1).getName());
    }

    @Test
    public void testCounterpartyFilteredListRequest() throws Exception {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");
        Attribute<String> stringAttribute = new Attribute<>("af686e1c-adba-11e7-7a34-5acf003d7f2e", AttributeType.TEXT, new AttributeValue<>("string"));
        counterparty.getAttributes().add(stringAttribute);
        Attribute<Long> longAttribute = new Attribute<>("af687111-adba-11e7-7a34-5acf003d7f2f", AttributeType.LONG, new AttributeValue<>(100L));
        counterparty.getAttributes().add(longAttribute);
        // Потому что наш API обрезает секунды при проставлении значения в доп. поле =_=
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Attribute<Date> timeAttribute = new Attribute<>("af6872f9-adba-11e7-7a34-5acf003d7f30", AttributeType.TIME, new AttributeValue<>(calendar.getTime()));
        counterparty.getAttributes().add(timeAttribute);

        Counterparty cp1 = new Counterparty();
        cp1.setName("Петюня братюня");
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(cp1).execute();
        cp1 = (Counterparty) response.getEntities().get(0);
        Attribute<Counterparty> counterpartyAttribute = new Attribute<>("af6874ef-adba-11e7-7a34-5acf003d7f31", AttributeType.COUNTERPARTY, new AttributeValue<>(cp1));
        counterparty.getAttributes().add(counterpartyAttribute);

        Attribute<Double> doubleAttribute = new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", AttributeType.DOUBLE, new AttributeValue<>(20.00));
        counterparty.getAttributes().add(doubleAttribute);
        Attribute<Boolean> booleanAttribute = new Attribute<>("af687e1c-adba-11e7-7a34-5acf003d7f34", AttributeType.BOOLEAN, new AttributeValue<>(true));
        counterparty.getAttributes().add(booleanAttribute);
        Attribute<String> textAttribute = new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", AttributeType.TEXT, new AttributeValue<>("text attribute"));
        counterparty.getAttributes().add(textAttribute);
        Attribute<String> linkAttribute = new Attribute<>("af688152-adba-11e7-7a34-5acf003d7f36", AttributeType.LINK, new AttributeValue<>("link.link.link"));
        counterparty.getAttributes().add(linkAttribute);

        CustomEntity customEntity = new CustomEntity();
        customEntity.setName("must be ensured");

        Attribute<CustomEntity> customEntityAttribute = new Attribute<>("37caf134-adbe-11e7-6b01-4b1d003e24b7", AttributeType.CUSTOMENTITY, new AttributeValue<>(customEntity));
        counterparty.getAttributes().add(customEntityAttribute);

        response = api.entity(Type.COUNTERPARTY).create(counterparty).execute();
        assertEquals(response.getStatus(), 200);

        Counterparty counterparty1 = new Counterparty();
        counterparty1.setName("Петя с иНН");
        counterparty1.setInn("7710152115");
        Attribute<Double> doubleAttribute1 = new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", AttributeType.DOUBLE, new AttributeValue<>(25.01));
        counterparty1.getAttributes().add(doubleAttribute1);
        api.entity(Type.COUNTERPARTY).create(counterparty1).execute();

        response = api.entity(Type.COUNTERPARTY).list().execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() >= 3);

        response = api
                .entity(Type.COUNTERPARTY)
                .list().buildFilter()
                .filter("inn", FilterOperator.EQUALS, "7710152115")
                .filter("inn", FilterOperator.EQUALS, "7710152113")
                .build()
                .limit(100)
                .execute();
        assertEquals(response.getStatus(), 200);
        List<Counterparty> list = (List<Counterparty>) response.getEntities();
        list.forEach(c -> assertTrue(c.getInn().equals("7710152115") || c.getInn().equals("7710152113")));

        response = api.entity(Type.COUNTERPARTY)
                .list().buildFilter()
                .filter("inn", FilterOperator.EQUALS, "7710152115")
                .build()
                .limit(100)
                .execute();
        assertEquals(response.getStatus(), 200);
        list = (List<Counterparty>) response.getEntities();
        list.forEach(c -> assertTrue(c.getInn().equals("7710152115")));


        response = api.entity(Type.COUNTERPARTY)
                .list().buildFilter()
                .filter(counterpartyAttribute, FilterOperator.EQUALS, counterpartyAttribute.getValue())
                .build()
                .execute();
        assertEquals(response.getStatus(), 200);
        list = (List<Counterparty>) response.getEntities();
        list.forEach(c -> {
            assertTrue(c.getAttribute(counterpartyAttribute.getId()).isPresent());
            Counterparty cpAttr = (Counterparty) c.getAttribute(counterpartyAttribute.getId()).get().getValue().getValue();
            assertEquals(cpAttr.getId().getValue(), counterpartyAttribute.getValue().getValue().getId().getValue());
        });

    }

    @Test
    public void testCounterpartyUpdate() throws Exception{
        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");
        AgentAccount account = new AgentAccount();
        account.setDefault(true);
        account.setAccountNumber("1231232131231312311323");
        counterparty.getAccounts().add(account);
        Attribute<String> stringAttribute = new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", AttributeType.TEXT, new AttributeValue<>("string"));
        counterparty.getAttributes().add(stringAttribute);
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(counterparty).addExpand("accounts").execute();
        assertEquals(200, response.getStatus());
        counterparty = (Counterparty) response.getEntities().get(0);

        assertEquals(1, counterparty.getAccounts().size());
        assertEquals(1, counterparty.getAttributes().size());
        assertEquals("af687f96-adba-11e7-7a34-5acf003d7f35", counterparty.getAttributes().iterator().next().getId());
        counterparty.setName("Not very Awesome Bro");
        counterparty.getAccounts().get(0).setAccountNumber("777777777777");
        ((Attribute<String>)counterparty.getAttributes().iterator().next()).setValue(new AttributeValue<>("kurica"));

        response = api.entity(Type.COUNTERPARTY).id(counterparty.getId()).update(counterparty).addExpand("accounts").execute();
        assertEquals(200, response.getStatus());
        counterparty = (Counterparty) response.getEntities().get(0);
        assertEquals(1, counterparty.getAccounts().size());
        assertEquals(1, counterparty.getAttributes().size());
        assertEquals("Not very Awesome Bro", counterparty.getName());
        assertEquals("777777777777", counterparty.getAccounts().get(0).getAccountNumber());
        assertEquals("af687f96-adba-11e7-7a34-5acf003d7f35", counterparty.getAttributes().iterator().next().getId());
        assertEquals("kurica", ((Attribute<String>)counterparty.getAttributes().iterator().next()).getValue().getValue());

    }

    @Test
    public void testFilterCounterpartyByEntityId() {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        AttributeValue<String> attributeValue = new AttributeValue<>("русские буквы и пробелы " + UUID.randomUUID());
        Attribute<String> stringAttribute = new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", AttributeType.TEXT, attributeValue);

        ApiResponse response = api.entity(Type.COUNTERPARTY)
                .list().buildFilter()
                .filter(stringAttribute, FilterOperator.EQUALS, attributeValue)
                .build()
                .execute();
        assertEquals(200, response.getStatus());
        assertEquals(0, response.getEntities().size());

        counterparty.getAttributes().add(stringAttribute);

        api.entity(Type.COUNTERPARTY).create(counterparty).execute();

        response = api.entity(Type.COUNTERPARTY)
                .list().buildFilter()
                .filter(stringAttribute, FilterOperator.EQUALS, attributeValue)
                .build()
                .execute();
        assertEquals(200, response.getStatus());
        assertEquals(1, response.getEntities().size());
    }

    private void checkAttributesEquality(IEntityWithAttributes actual, IEntityWithAttributes expected) {
        assertEquals(actual.getAttributes().size(), expected.getAttributes().size());
        for (Attribute<?> attribute : actual.getAttributes()) {
            Optional<Attribute<?>> attributeOptional = expected.getAttribute(attribute.getId());
            assertTrue(attributeOptional.isPresent());
            Attribute<?> attr2 =attributeOptional .get();
            assertEquals(attr2.getType(), attribute.getType());
            if (attribute.getValue().getValue() instanceof Entity) {
                Entity entity = (Entity) attribute.getValue().getValue();
                Entity entity2 = (Entity) attr2.getValue().getValue();
                assertEquals(Type.find(entity.getClass()), Type.find(entity2.getClass()));
                if (!(entity instanceof CustomEntity)) {
                    // because CustomEntity might be ensured while put in attributes array
                    assertEquals(entity.getId().getValue(), entity2.getId().getValue());
                }
            } else {
                assertEquals(attr2.getValue().getValue(), attribute.getValue().getValue());
            }
        }
    }


}
