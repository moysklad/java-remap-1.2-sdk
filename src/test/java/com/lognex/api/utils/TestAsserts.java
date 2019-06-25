package com.lognex.api.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface TestAsserts {
    default void assertApiError(LognexApiException e, int expectedHttpStatus, int expectedApiErrorCode, String expectedApiErrorText) {
        assertApiError(
                e, expectedHttpStatus, Collections.singletonList(Pair.of(expectedApiErrorCode, expectedApiErrorText))
        );
    }

    default void assertApiError(LognexApiException e, int expectedHttpStatus, Collection<Pair<Integer, String>> expectedApiErrorData) {
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
}
