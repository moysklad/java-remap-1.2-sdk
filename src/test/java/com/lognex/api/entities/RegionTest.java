package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RegionTest extends EntityTestBase {
    @Test
    public void getAllTest() throws IOException, ApiClientException {
        ListEntity<Region> regions = api.entity().region().get();

        assertEquals(Integer.valueOf(86), regions.getMeta().getSize());
        assertEquals("Краснодарский край", regions.getRows().get(22).getName());
        assertEquals("23", regions.getRows().get(22).getCode());
        assertEquals("23", regions.getRows().get(22).getExternalCode());
    }

    @Test
    public void getOneTest() throws IOException, ApiClientException {
        Region region = api.entity().region().get("00000000-0000-0000-0000-000000000023");

        assertEquals("00000000-0000-0000-0000-000000000023", region.getId());
        assertEquals("23", region.getCode());

        region = api.entity().region().get(new Region("00000000-0000-0000-0000-000000000020"));

        assertEquals("00000000-0000-0000-0000-000000000020", region.getId());
        assertEquals("20", region.getCode());
    }
}
