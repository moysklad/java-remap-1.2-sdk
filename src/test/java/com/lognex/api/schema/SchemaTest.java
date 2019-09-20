package com.lognex.api.schema;

import com.lognex.api.ApiClient;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.documents.CustomerOrder;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestConstants;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Ignore;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Test;


public class SchemaTest implements TestAsserts, TestRandomizers {
    private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);

    @Ignore
    @Test
    public void testCounterpartySchema() throws Exception {
        //todo register new company
        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
        Schema schema = SchemaMapper.readSchema("counterparty.json");
        SchemaValidator<Counterparty> schemaValidator = new SchemaValidator<>(
                schema,
                api,
                api.entity().counterparty().path(),
                Counterparty.class);
        schemaValidator.check();
    }
    @Ignore
    @Test
    public void testCostomerOrderSchema() throws Exception {
        //todo register new company
        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
        Schema schema = SchemaMapper.readSchema("customerorder.json");
        SchemaValidator<CustomerOrder> schemaValidator = new SchemaValidator<>(
                schema,
                api,
                api.entity().customerorder().path(),
                CustomerOrder.class);
        schemaValidator.check();
    }
}
