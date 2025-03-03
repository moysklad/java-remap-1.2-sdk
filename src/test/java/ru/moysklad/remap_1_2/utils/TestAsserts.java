package ru.moysklad.remap_1_2.utils;

import org.apache.commons.lang3.tuple.Pair;
import ru.moysklad.remap_1_2.entities.Address;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface TestAsserts {
    default void assertApiError(ApiClientException e, int expectedHttpStatus, int expectedApiErrorCode, String expectedApiErrorText) {
        assertApiError(
                e, expectedHttpStatus, Collections.singletonList(Pair.of(expectedApiErrorCode, expectedApiErrorText))
        );
    }

    default void assertApiError(ApiClientException e, int expectedHttpStatus, Collection<Pair<Integer, String>> expectedApiErrorData) {
        assertEquals(expectedHttpStatus, e.getStatusCode());
        assertNotNull(e.getErrorResponse());
        assertNotNull(e.getErrorResponse().getErrors());
        assertEquals(expectedApiErrorData.size(), e.getErrorResponse().getErrors().size());

        int index = 0;
        for (Pair<Integer, String> data : expectedApiErrorData) {
            assertEquals(data.getKey(), e.getErrorResponse().getErrors().get(index).getCode());
            assertEquals(
                    data.getValue(),
                    e.getErrorResponse().getErrors().get(index++).getError()
            );
        }
    }

    default void assertAddressFull(Address expected, Address real) {
        assertEquals(expected.getPostalCode(), real.getPostalCode());
        assertEquals(expected.getCountry().getMeta().getUuidHref(), real.getCountry().getMeta().getUuidHref());
        assertEquals(expected.getRegion().getMeta().getUuidHref(), real.getRegion().getMeta().getUuidHref());
        assertEquals(expected.getCity(), real.getCity());
        assertEquals(expected.getStreet(), real.getStreet());
        assertEquals(expected.getHouse(), real.getHouse());
        assertEquals(expected.getApartment(), real.getApartment());
        assertEquals(expected.getAddInfo(), real.getAddInfo());
        assertEquals(expected.getComment(), real.getComment());
        assertEquals(expected.getCodeFias(), real.getCodeFias());
    }
}
