package com.lognex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.entities.*;
import com.lognex.api.entities.documents.CustomerOrder;
import com.lognex.api.responses.CounterpartyMetadataListResponse;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PilotTest {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final LognexApi api = new LognexApi(
            "https://online-20.moysklad.ru",
            "admin@cddee50667affcf",
            "1E3b81De3cb24E5a"
    );

    /**
     * Тест на получение списка контрагентов
     */
    @Test
    public void test1() throws Exception {
        ListResponse<Counterparty> cp = api.entity().
                counterparty().
                get();

        System.out.println(gson.toJson(cp));
    }

    /**
     * Тест на получение контрагента по ID
     */
    @Test
    public void test2() throws Exception {
        Counterparty cp = api.entity().
                counterparty("114539eb-582d-11e8-7ae5-884a0001ba21").
                get();

        System.out.println(gson.toJson(cp));
    }

    /**
     * Тест клонирования
     */
    @Test
    public void test3() {
        Counterparty cp = new Counterparty();
        cp.name = "Новый контр1";
        cp.state = new State();

        System.out.println(cp.state);

        Counterparty cp1 = Entity.clone(cp);
        System.out.println(cp1.state);
    }

    /**
     * Тест на попытку создания пустого контрагента
     */
    @Test
    public void test4() {
        Counterparty cp = new Counterparty();

        try {
            api.entity().
                    counterparty().
                    post(cp);
        } catch (Exception e) {
            assertTrue(e instanceof LognexApiException);

            LognexApiException lae = (LognexApiException) e;
            assertEquals(412, lae.getStatusCode());
            assertNotNull(lae.getErrorResponse());
            assertFalse(lae.getErrorResponse().errors.isEmpty());
            assertEquals(1, lae.getErrorResponse().errors.size());
            assertNotNull(lae.getErrorResponse().errors.get(0).code);
            assertEquals(3000, (int) lae.getErrorResponse().errors.get(0).code);
        }
    }

    /**
     * Тест на создание контрагента
     */
    @Test
    public void test5() throws Exception {
        Counterparty cp = new Counterparty();
        cp.name = "Новый контр1";

        api.entity().
                counterparty().
                post(cp);

        System.out.println(gson.toJson(cp));

        assertNotNull(cp.id);
        assertFalse(cp.id.trim().isEmpty());
        assertEquals(1,
                api.entity().
                        counterparty().
                        get().rows.stream().
                        filter(c -> c.id.equals(cp.id)).
                        count()
        );
    }

    /**
     * Тест на дополучение полей сущности
     */

    @Test
    public void test6() throws Exception {
        Counterparty cp = api.entity().
                counterparty("114539eb-582d-11e8-7ae5-884a0001ba21").
                get();

        System.out.println(gson.toJson(cp.state));

        cp.state.fetch(api);

        System.out.println(gson.toJson(cp.state));
    }

    /**
     * Тест удаления
     */
    @Test
    public void test7() throws Exception {
        Counterparty cp = new Counterparty();
        cp.name = "Контрагент для удаления";

        api.entity().
                counterparty().
                post(cp);

        api.entity().
                counterparty(cp.id).
                delete();

        assertEquals(0,
                api.entity().
                        counterparty().
                        get().rows.stream().
                        filter(c -> c.id.equals(cp.id)).
                        count()
        );
    }

    /**
     * Тест списка метаданных
     */
    @Test
    public void test8() throws Exception {
        CounterpartyMetadataListResponse md = api.entity().
                counterparty().
                metadata();

        System.out.println(gson.toJson(md));
    }

    /**
     * Тест изменения контрагента
     */
    @Test
    public void test9() throws Exception {
        Counterparty cp = new Counterparty();
        cp.name = "Контрагент для изменения";

        api.entity().
                counterparty().
                post(cp);

        assertEquals("Контрагент для изменения", api.entity().counterparty(cp.id).get().name);

        cp.name = "Изменённый контрагент";

        api.entity().
                counterparty(cp.id).
                put(cp);

        assertEquals("Изменённый контрагент", api.entity().counterparty(cp.id).get().name);
    }

    /**
     * Тест работы с документами
     */
    @Test
    public void test10() throws Exception {
        {
            ListResponse<CustomerOrder> res = api.entity().customerorder().get();
            System.out.println(gson.toJson(res));
        }

        {
            ListResponse<Counterparty> cags = api.entity().counterparty().get();
            ListResponse<Organization> orgs = api.entity().organization().get();

            CustomerOrder co = new CustomerOrder();
            co.agent = cags.rows.stream().filter(e -> e.name.equals("Новый контр1")).findFirst().get();
            co.organization = orgs.rows.stream().filter(e -> e.name.equals("cddee50667affcf")).findFirst().get();

            api.entity().customerorder().post(co);
            System.out.println(gson.toJson(co));
        }

        /*
            Другие методы для работы с документами:

                api.entity().purchaseorder().metadata();
                api.entity().purchaseorder().metadata().attributes(attr_id);

                api.entity().purchaseorder().template();
                api.entity().purchaseorder().template(doc);

                api.entity().purchaseorder(doc_id).get();
                api.entity().purchaseorder(doc_id).put(doc);
                api.entity().purchaseorder(doc_id).delete();
                api.entity().purchaseorder(doc_id).positions().get();
                api.entity().purchaseorder(doc_id).positions().post(posList);
                api.entity().purchaseorder(doc_id).positions(pos_id).get();
                api.entity().purchaseorder(doc_id).positions(pos_id).put(pos);
                api.entity().purchaseorder(doc_id).positions(pos_id).delete();
         */
    }

    /**
     * Тест работы с товарами
     */
    @Test
    public void test11() throws Exception {
        {
            ListResponse<Product> prList = api.entity().product().get();
            System.out.println(gson.toJson(prList));
        }

        {
            Product newProduct = new Product();
            newProduct.name = "Новый товар " + new Date().getTime();

            api.entity().product().post(newProduct);
            Optional<Product> o1 = api.entity().product().get().rows.stream().filter(p -> p.name.equals(newProduct.name)).findFirst();
            assertTrue(o1.isPresent());
        }

        {
            Product newProduct = new Product();
            newProduct.name = "Новый товар " + new Date().getTime();

            api.entity().product().post(newProduct);
            Optional<Product> o1 = api.entity().product().get().rows.stream().filter(p -> p.name.equals(newProduct.name)).findFirst();
            assertTrue(o1.isPresent());

            api.entity().product().delete(newProduct.id);
            Optional<Product> o2 = api.entity().product().get().rows.stream().filter(p -> p.name.equals(newProduct.name)).findFirst();
            assertFalse(o2.isPresent());
        }
    }

    /**
     * Тест работы с товарами с модификациями
     */
    @Test
    public void test12() throws Exception {
        Product pr = api.entity().product().get().rows.stream().filter(p -> p.name.equals("Модтовар")).findFirst().get();

        api.entity().variant().get().rows.stream().filter(v -> v.product.meta.href.equals(pr.meta.href)).forEach(v -> {
            System.out.println(v.name);
        });

        List<Characteristic> chars = api.entity().variant().metadata().characteristics;
        Characteristic ch1 = chars.stream().filter(c -> c.name.equals("Цвет")).findFirst().get();
        Characteristic ch2 = chars.stream().filter(c -> c.name.equals("Размер")).findFirst().get();
        Characteristic ch3 = chars.stream().filter(c -> c.name.equals("Газированность")).findFirst().get();

        Variant newVariant = new Variant();
        newVariant.product = pr;
        newVariant.characteristics = new ArrayList<>();

        ch1.value = "Красивый";
        ch2.value = "В самый раз";
        ch3.value = "???";

        newVariant.characteristics.add(ch1);
        newVariant.characteristics.add(ch2);
        newVariant.characteristics.add(ch3);

        api.entity().variant().post(newVariant);

        System.out.println();
        System.out.println();

        api.entity().variant().get().rows.stream().filter(v -> v.product.meta.href.equals(pr.meta.href)).forEach(v -> {
            System.out.println(v.name);
        });
    }
}
