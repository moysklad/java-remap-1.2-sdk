package com.lognex.api;

import com.lognex.api.model.base.field.CompanyType;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.CustomEntity;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.attribute.AttributeValue;
import com.lognex.api.request.filter.FilterOperator;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.Type;
import org.junit.Test;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntityTest {

    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testCreateAndGetCounterparty() throws Exception{

        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");
        counterparty.getAttributes().add(new Attribute<>("af686e1c-adba-11e7-7a34-5acf003d7f2e", "string", new AttributeValue<>("string")));
        counterparty.getAttributes().add(new Attribute<>("af687111-adba-11e7-7a34-5acf003d7f2f", "long", new AttributeValue<>(100L)));
        // Потому что наш API обрезает секунды при проставлении значения в доп. поле =_=
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        counterparty.getAttributes().add(new Attribute<>("af6872f9-adba-11e7-7a34-5acf003d7f30", "time", new AttributeValue<>(calendar.getTime())));

        Counterparty cp1 = new Counterparty();
        cp1.setName("Петюня братюня");
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(cp1).execute();
        cp1 = (Counterparty) response.getEntities().get(0);
        counterparty.getAttributes().add(new Attribute<>("af6874ef-adba-11e7-7a34-5acf003d7f31", "counterparty", new AttributeValue<>(cp1)));


        counterparty.getAttributes().add(new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", "double", new AttributeValue<>(20.00)));
        counterparty.getAttributes().add(new Attribute<>("af687e1c-adba-11e7-7a34-5acf003d7f34","boolean", new AttributeValue<>(true)));
        counterparty.getAttributes().add(new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", "text", new AttributeValue<>("text attribute")));
        counterparty.getAttributes().add(new Attribute<>("af688152-adba-11e7-7a34-5acf003d7f36", "link", new AttributeValue<>("link.link.link")));

        CustomEntity customEntity = new CustomEntity();
        customEntity.setName("must be ensured");

        counterparty.getAttributes().add(new Attribute<>("37caf134-adbe-11e7-6b01-4b1d003e24b7", "customentity", new AttributeValue<>(customEntity)));


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
    public void testCounterpartyFilteredListRequest() throws Exception {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("AwesomeBro");
        counterparty.setCompanyType(CompanyType.LEGAL);
        counterparty.setLegalTitle("OOO AwesomeBro");
        counterparty.setInn("7710152113");
        counterparty.setKpp("771001001");
        counterparty.setOgrn("1027700505348");
        counterparty.setOkpo("02278679");
        Attribute<String> stringAttribute = new Attribute<>("af686e1c-adba-11e7-7a34-5acf003d7f2e", "string", new AttributeValue<>("string"));
        counterparty.getAttributes().add(stringAttribute);
        Attribute<Long> longAttribute = new Attribute<>("af687111-adba-11e7-7a34-5acf003d7f2f", "long", new AttributeValue<>(100L));
        counterparty.getAttributes().add(longAttribute);
        // Потому что наш API обрезает секунды при проставлении значения в доп. поле =_=
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Attribute<Date> timeAttribute = new Attribute<>("af6872f9-adba-11e7-7a34-5acf003d7f30", "time", new AttributeValue<>(calendar.getTime()));
        counterparty.getAttributes().add(timeAttribute);

        Counterparty cp1 = new Counterparty();
        cp1.setName("Петюня братюня");
        ApiResponse response = api.entity(Type.COUNTERPARTY).create(cp1).execute();
        cp1 = (Counterparty) response.getEntities().get(0);
        Attribute<Counterparty> counterpartyAttribute = new Attribute<>("af6874ef-adba-11e7-7a34-5acf003d7f31", "counterparty", new AttributeValue<>(cp1));
        counterparty.getAttributes().add(counterpartyAttribute);

        Attribute<Double> doubleAttribute = new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", "double", new AttributeValue<>(20.00));
        counterparty.getAttributes().add(doubleAttribute);
        Attribute<Boolean> booleanAttribute = new Attribute<>("af687e1c-adba-11e7-7a34-5acf003d7f34", "boolean", new AttributeValue<>(true));
        counterparty.getAttributes().add(booleanAttribute);
        Attribute<String> textAttribute = new Attribute<>("af687f96-adba-11e7-7a34-5acf003d7f35", "text", new AttributeValue<>("text attribute"));
        counterparty.getAttributes().add(textAttribute);
        Attribute<String> linkAttribute = new Attribute<>("af688152-adba-11e7-7a34-5acf003d7f36", "link", new AttributeValue<>("link.link.link"));
        counterparty.getAttributes().add(linkAttribute);

        CustomEntity customEntity = new CustomEntity();
        customEntity.setName("must be ensured");

        Attribute<CustomEntity> customEntityAttribute = new Attribute<>("37caf134-adbe-11e7-6b01-4b1d003e24b7", "customentity", new AttributeValue<>(customEntity));
        counterparty.getAttributes().add(customEntityAttribute);

        response = api.entity(Type.COUNTERPARTY).create(counterparty).execute();
        assertEquals(response.getStatus(), 200);

        Counterparty counterparty1 = new Counterparty();
        counterparty1.setName("Петя с иНН");
        counterparty1.setInn("7710152115");
        Attribute<Double> doubleAttribute1 = new Attribute<>("af6879ea-adba-11e7-7a34-5acf003d7f33", "double", new AttributeValue<>(25.01));
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
            assertNotNull(c.getAttribute(counterpartyAttribute.getId()));
            Counterparty cpAttr = (Counterparty) c.getAttribute(counterpartyAttribute.getId()).getValue().getValue();
            assertEquals(cpAttr.getId().getValue(), counterpartyAttribute.getValue().getValue().getId().getValue());
        });

    }

    private void checkAttributesEquality(IEntityWithAttributes en1, IEntityWithAttributes en2){
        assertEquals(en1.getAttributes().size(), en2.getAttributes().size());
        for (Attribute<?> attribute : en1.getAttributes()){
            Attribute<?> attr2 = en2.getAttribute(attribute.getId());
            assertTrue(attr2 != null);
            assertEquals(attr2.getType(), attribute.getType());
            if (attribute.getValue().getValue() instanceof AbstractEntity){
                AbstractEntity entity = (AbstractEntity) attribute.getValue().getValue();
                AbstractEntity entity2 = (AbstractEntity) attr2.getValue().getValue();
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
