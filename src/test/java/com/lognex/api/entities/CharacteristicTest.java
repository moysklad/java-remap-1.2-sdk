package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.products.Variant;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CharacteristicTest extends EntityTestBase {
    @Test
    public void testGet() throws IOException, ApiClientException {
        List<Variant.Characteristic> existedCharacteristics =
                api.entity().variant().characteristics().get().getCharacteristics();
        Set<String> existedIDs = existedCharacteristics == null ? Collections.emptySet() :
                existedCharacteristics.stream()
                .map(MetaEntity::getId)
                .collect(Collectors.toSet());

        String name1 = simpleEntityManager.randomString();
        Variant.Characteristic characteristic1 = api.entity().variant().characteristics().create(byName(name1));
        assertNotNull(characteristic1.getId());
        assertEquals(name1, characteristic1.getName());

        String name2 = simpleEntityManager.randomString();
        Variant.Characteristic characteristic2 = api.entity().variant().characteristics().create(byName(name2));

        Set<String> actualCharacteristics = api.entity().variant().characteristics().get()
                .getCharacteristics().stream()
                .filter(ch -> !existedIDs.contains(ch.getId())) //в базе могли быть характеристики до теста
                .map(MetaEntity::getName)
                .collect(Collectors.toSet());
        Set<String> expectedCharacteristics = new HashSet<>(Arrays.asList(characteristic1.getName(), characteristic2.getName()));
        assertEquals(expectedCharacteristics, actualCharacteristics);
    }

    private Variant.Characteristic byName(String name) {
        Variant.Characteristic result = new Variant.Characteristic(name);
        result.setName(name);
        return result;
    }

    @Test
    public void testMassCreate() throws IOException, ApiClientException {
        Set<String> names = new HashSet<>(Arrays.asList(simpleEntityManager.randomString(), simpleEntityManager.randomString()));
        List<Variant.Characteristic> itemsToCreate = names.stream().map(this::byName).collect(Collectors.toList());
        Set<String> received = api.entity().variant().characteristics().create(itemsToCreate).stream()
                .map(MetaEntity::getName)
                .collect(Collectors.toSet());
        assertEquals(names, received);
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().variant().characteristics();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Variant.Characteristic.class;
    }
}
