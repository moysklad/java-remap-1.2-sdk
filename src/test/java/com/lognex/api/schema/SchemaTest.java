package com.lognex.api.schema;

import com.lognex.api.LognexApi;
import com.lognex.api.TestAsserts;
import com.lognex.api.TestRandomizers;
import com.lognex.api.entities.agents.CounterpartyEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class SchemaTest implements TestAsserts, TestRandomizers {
    private static final Logger logger = LogManager.getLogger(SchemaTest.class);

    @Test
    public void testCounterpartySchema() throws Exception {
        //todo register new company
//        LognexApi api = new LognexApi(
//                System.getenv("API_HOST"),
//                System.getenv("login"),
//                System.getenv("password")
//        );
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
               "admin@ddd1",
                "123qwe"
        ).timeWithMilliseconds();
        Schema schema = SchemaMapper.readSchema("counterparty.json");
        SchemaValidator<CounterpartyEntity> schemaValidator = new SchemaValidator<>(
                schema,
                api,
                api.entity().counterparty().path(),
                CounterpartyEntity.class);
        schemaValidator.check();
    }

    @Test
    public void testReport() {
        SchemaReport schemaReport = new SchemaReport();
        schemaReport.chapter("1");

        schemaReport.chapter("1-1");
        schemaReport.endChapter();

        schemaReport.chapter("1-2");
        schemaReport.log("message");
        schemaReport.endChapter();

        schemaReport.endChapter();
        logger.info(schemaReport.allLog());
        logger.info(schemaReport.problemLog());
    }
}
