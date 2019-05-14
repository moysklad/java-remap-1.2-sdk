package com.lognex.api;

import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.io.IOException;
import java.time.temporal.ChronoField;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MillisecondsTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_millisecondsHeader() throws IOException, LognexApiException {
        LognexApi api_s = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        LognexApi api_ms = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        ).timeWithMilliseconds();

        CounterpartyEntity cag = api_s.entity().counterparty().get().getRows().get(0);
        assertEquals(0, cag.getCreated().get(ChronoField.MILLI_OF_SECOND));

        CounterpartyEntity cag1 = api_ms.entity().counterparty().get().getRows().get(0);
        assertNotEquals(0, cag1.getCreated().get(ChronoField.MILLI_OF_SECOND));
    }
}
