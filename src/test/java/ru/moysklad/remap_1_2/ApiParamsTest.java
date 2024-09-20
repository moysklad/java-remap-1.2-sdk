package ru.moysklad.remap_1_2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.Inventory;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.FilterType.equivalence;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.*;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static ru.moysklad.remap_1_2.utils.params.OffsetParam.offset;
import static ru.moysklad.remap_1_2.utils.params.OrderParam.Direction.asc;
import static ru.moysklad.remap_1_2.utils.params.OrderParam.Direction.desc;
import static ru.moysklad.remap_1_2.utils.params.OrderParam.order;
import static ru.moysklad.remap_1_2.utils.params.SearchParam.search;
import static org.junit.Assert.*;

public class ApiParamsTest implements TestRandomizers {
    private ApiClient api, mockApi;
    private RequestLogHttpClient logHttpClient;
    private MockHttpClient mockHttpClient;
    private String host;

    @Before
    public void init() {
        logHttpClient = new RequestLogHttpClient();
        api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD"),
                logHttpClient
        );

        host = api.getHost();

        mockHttpClient = new MockHttpClient();
        mockApi = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD"),
                mockHttpClient
        );
    }

    @After
    public void antiLimits() throws InterruptedException {
        Thread.sleep(1500); // Защита от лимитов
    }

    @Test
    public void test_expand() throws IOException, ApiClientException {
        /*
            Без expand
         */

        ListEntity<Counterparty> listWithoutExpand = api.entity().counterparty().get();
        Counterparty elementWithoutExpand = listWithoutExpand.getRows().get(0);
        assertNull(elementWithoutExpand.getGroup().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            С expand
         */

        ListEntity<Counterparty> listWithExpand = api.entity().counterparty().get(limit(10), expand("group"));
        Counterparty elementWithExpand = listWithExpand.getRows().get(0);
        assertNotNull(elementWithExpand.getGroup().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?expand=group&limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            С вложенным expand
         */

        ListEntity<Counterparty> listWithNestedExpand = api.entity().counterparty().get(limit(10), expand("owner.group"));
        Counterparty elementWithNestedExpand = listWithNestedExpand.getRows().get(0);
        assertNull(elementWithNestedExpand.getGroup().getName());
        assertNotNull(elementWithNestedExpand.getOwner().getName());
        assertNotNull(elementWithNestedExpand.getOwner().getGroup().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?expand=owner.group&limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_multipleExpand() throws IOException, ApiClientException {
        /*
            expand двух полей
         */

        ListEntity<Counterparty> expand2 = api.entity().counterparty().get(limit(10), expand("group", "owner"));
        Counterparty expandElement2 = expand2.getRows().get(0);
        assertNotNull(expandElement2.getGroup().getName());
        assertNotNull(expandElement2.getOwner().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?expand=group%2Cowner&limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            expand двух полей, одно из которых -- вложенное
         */

        ListEntity<Counterparty> expandNested = api.entity().counterparty().get(limit(10), expand("group", "owner.group"));
        Counterparty expandElementNested = expandNested.getRows().get(0);
        assertNotNull(expandElementNested.getGroup().getName());
        assertNotNull(expandElementNested.getOwner().getGroup().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?expand=group%2Cowner.group&limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            expand двух полей двумя отдельными параметрами
         */

        ListEntity<Counterparty> expand22 = api.entity().counterparty().get(limit(10), expand("group"), expand("owner"));
        Counterparty expandElement22 = expand22.getRows().get(0);
        assertNotNull(expandElement22.getGroup().getName());
        assertNotNull(expandElement22.getOwner().getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?expand=group%2Cowner&limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_filter() throws IOException, ApiClientException {
        /*
            Без фильтрации
         */

        ListEntity<Counterparty> dataWithoutFilter = api.entity().counterparty().get();
        int size = dataWithoutFilter.getMeta().getSize();
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            C фильтром равенства
         */

        ListEntity<Counterparty> dataWithEqFilter1 = api.entity().counterparty().get(
                filterEq("name", "ООО \"Поставщик\"")
        );
        assertEquals(1, dataWithEqFilter1.getRows().size());
        assertEquals("ООО \"Поставщик\"", dataWithEqFilter1.getRows().get(0).getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?filter=name" + URLEncoder.encode("=ООО \"Поставщик\"", "UTF-8"),
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            С фильтром неравенства
         */

        ListEntity<Counterparty> dataWithEqFilter2 = api.entity().counterparty().get(
                filterNot("name", "ООО \"Поставщик\"")
        );
        assertEquals(size - 1, (int) dataWithEqFilter2.getMeta().getSize());
        assertNotEquals("ООО \"Поставщик\"", dataWithEqFilter2.getRows().get(0).getName());
        assertNotEquals("ООО \"Поставщик\"", dataWithEqFilter2.getRows().get(1).getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?filter=name" + URLEncoder.encode("!=ООО \"Поставщик\"", "UTF-8"),
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            С фильтром подобия
         */

        ListEntity<Counterparty> dataWithEqFilter3 = api.entity().counterparty().get(
                filter("name", equivalence, "ООО")
        );
        assertEquals(2, dataWithEqFilter3.getRows().size());
        assertNotEquals("Розничный покупатель", dataWithEqFilter3.getRows().get(0).getName());
        assertNotEquals("Розничный покупатель", dataWithEqFilter3.getRows().get(1).getName());
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?filter=name" + URLEncoder.encode("~ООО", "UTF-8"),
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_multipleFilter() throws IOException, ApiClientException {
        GlobalMetadata metadataWithFilter = api.entity().metadata().get(
                filterEq("type", "product"),
                filterEq("type", "organization"),
                filterEq("type", "demand")
        );
        assertNull(metadataWithFilter.getCounterparty());
        assertNotNull(metadataWithFilter.getProduct());
        assertNotNull(metadataWithFilter.getOrganization());
        assertNotNull(metadataWithFilter.getDemand());
        assertEquals(
                host + "/api/remap/1.2/entity/metadata/?filter=" +
                        "type" + URLEncoder.encode("=product;", "UTF-8") +
                        "type" + URLEncoder.encode("=organization;", "UTF-8") +
                        "type" + URLEncoder.encode("=", "UTF-8") + "demand",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_hrefFilter() throws IOException, ApiClientException {
        Inventory inventory = new Inventory();
        inventory.setName("HrefFilter_inventory_" + randomStringTail());

        Store store = new Store();
        store.setName("HrefFilter_store_" + randomStringTail());
        store = api.entity().store().create(store);
        inventory.setStore(store);

        ListEntity<Organization> orgList = api.entity().organization().get();
        Optional<Organization> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(Organization::getCreated));
        Organization organizationEntity;
        if (orgOptional.isPresent()) {
            organizationEntity = orgOptional.get();
        } else {
            throw new IllegalStateException("Не удалось получить первое созданное юрлицо");
        }
        inventory.setOrganization(organizationEntity);

        inventory = api.entity().inventory().create(inventory);

        ListEntity<Inventory> inventoryWithFilter = api.entity().inventory().get(filterEq("store", new Store(store.getId())));
        assertEquals(1, inventoryWithFilter.getRows().size());
        assertEquals(inventory.getName(), inventoryWithFilter.getRows().get(0).getName());
        assertNotNull(inventoryWithFilter.getRows().get(0).getStore());
        assertEquals(host + "/api/remap/1.2/entity/inventory/?filter=store" +
                        URLEncoder.encode("=" + host + "/api/remap/1.2/entity/store/" + store.getId(), "UTF-8"),
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri());
    }

    @Test
    public void test_attributeFilter() throws IOException, ApiClientException {
        mockApi.entity().counterparty().get(
                filterEq(new AttributeEntity(Meta.Type.COUNTERPARTY, "ID", Attribute.Type.stringValue, "NOT_USED"), "VALUE")
        );
        assertEquals(
                host + "/api/remap/1.2/entity/counterparty/?filter=" +
                        URLEncoder.encode(host + "/api/remap/1.2/entity/counterparty/metadata/attributes/ID=VALUE", "UTF-8"),
                mockHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_limitOffset() throws IOException, ApiClientException {
        ListEntity<Uom> uomPlain = api.entity().uom().get();
        int actualSize = uomPlain.getMeta().getSize();
        assertTrue(actualSize >= 58);
        assertEquals(1000, (int) uomPlain.getMeta().getLimit());
        assertEquals(0, (int) uomPlain.getMeta().getOffset());
        assertEquals(actualSize, uomPlain.getRows().size());
        assertEquals(
                host + "/api/remap/1.2/entity/uom/",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        ListEntity<Uom> uomLimit = api.entity().uom().get(limit(10));
        assertEquals(actualSize, (int) uomLimit.getMeta().getSize());
        assertEquals(10, (int) uomLimit.getMeta().getLimit());
        assertEquals(0, (int) uomLimit.getMeta().getOffset());
        assertEquals(10, uomLimit.getRows().size());
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?limit=10",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        ListEntity<Uom> uomOffset = api.entity().uom().get(offset(50));
        assertEquals(actualSize, (int) uomOffset.getMeta().getSize());
        assertEquals(1000, (int) uomOffset.getMeta().getLimit());
        assertEquals(50, (int) uomOffset.getMeta().getOffset());
        assertEquals(actualSize - 50, uomOffset.getRows().size());
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?offset=50",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        int limit = actualSize - 55;
        ListEntity<Uom> uomLimitOffset = api.entity().uom().get(limit(limit), offset(52));
        assertEquals(actualSize, (int) uomLimitOffset.getMeta().getSize());
        assertEquals(limit, (int) uomLimitOffset.getMeta().getLimit());
        assertEquals(52, (int) uomLimitOffset.getMeta().getOffset());
        assertEquals(limit, uomLimitOffset.getRows().size());
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?offset=52&limit=" + limit,
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_order() throws IOException, ApiClientException {
        ListEntity<Uom> uomPlain = api.entity().uom().get(limit(100));
        List<String> listAsIs = uomPlain.getRows().stream().map(MetaEntity::getName).collect(Collectors.toList());

        /*
            Сортировка по одному параметру без указания направления сортировки
         */

        ListEntity<Uom> uomOrderByNameAsc = api.entity().uom().get(limit(100), order("name"));
        List<String> listSortedByNameAsc = uomOrderByNameAsc.getRows().stream().map(MetaEntity::getName).collect(Collectors.toList());
        assertNotEquals(listAsIs, listSortedByNameAsc);
        assertTrue(listSortedByNameAsc.containsAll(listAsIs));
        assertTrue(listAsIs.containsAll(listSortedByNameAsc));
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?limit=100&order=name",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            Сортировка по одному параметру с указанием направления сортировки
         */

        ListEntity<Uom> uomOrderByNameDesc = api.entity().uom().get(limit(100), order("name", desc));
        List<String> listSortedByNameDesc = uomOrderByNameDesc.getRows().stream().map(MetaEntity::getName).collect(Collectors.toList());
        assertNotEquals(listAsIs, listSortedByNameDesc);
        assertNotEquals(listSortedByNameAsc, listSortedByNameDesc);
        assertTrue(listSortedByNameDesc.containsAll(listAsIs));
        assertTrue(listAsIs.containsAll(listSortedByNameDesc));
        assertTrue(listSortedByNameDesc.containsAll(listSortedByNameAsc));
        assertTrue(listSortedByNameAsc.containsAll(listSortedByNameDesc));

        Collections.reverse(listSortedByNameAsc);
        assertEquals(listSortedByNameAsc, listSortedByNameDesc);
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?limit=100&order=name%2Cdesc",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );

        /*
            Сортировка по нескольким параметрам
         */

        ListEntity<Uom> uomOrderByTwoParams = api.entity().uom().get(limit(100), order("name", desc), order("id"), order("updated", asc));
        List<String> listSortedByTwoParams = uomOrderByTwoParams.getRows().stream().map(MetaEntity::getName).collect(Collectors.toList());
        assertNotEquals(listAsIs, listSortedByTwoParams);
        assertTrue(listSortedByTwoParams.containsAll(listAsIs));
        assertTrue(listAsIs.containsAll(listSortedByTwoParams));
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?limit=100&order=name%2Cdesc%3Bid%3Bupdated%2Casc",
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void test_search() throws IOException, ApiClientException {
        ListEntity<Uom> uomPlain = api.entity().uom().get(limit(100));
        ListEntity<Uom> uomSearch = api.entity().uom().get(search("мил"));

        Predicate<Uom> searchPredicate = o -> o.getName().toLowerCase().contains("мил")
                || (o.getDescription() != null && o.getDescription().toLowerCase().contains("мил"));
        assertTrue(uomSearch.getRows().stream().allMatch(searchPredicate));
        assertEquals(uomPlain.getRows().stream().filter(searchPredicate).count(), uomSearch.getRows().size());
        assertEquals(
                host + "/api/remap/1.2/entity/uom/?search=" + URLEncoder.encode("мил", "UTF-8"),
                logHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }
}