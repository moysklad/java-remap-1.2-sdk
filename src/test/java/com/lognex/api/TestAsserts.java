package com.lognex.api;

import com.lognex.api.utils.LognexApiException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface TestAsserts {
    default void assertApiError(LognexApiException e, int expectedHttpStatus, int expectedApiErrorCode, String expectedApiErrorText) {
        assertEquals(expectedHttpStatus, e.getStatusCode());
        assertNotNull(e.getErrorResponse());
        assertNotNull(e.getErrorResponse().getErrors());
        assertEquals(1, e.getErrorResponse().getErrors().size());
        assertEquals(expectedApiErrorCode, (int) e.getErrorResponse().getErrors().get(0).getCode());
        assertEquals(
                expectedApiErrorText,
                e.getErrorResponse().getErrors().get(0).getError()
        );
    }
}
