package com.lognex.api;

import com.lognex.api.endpoint.DocumentEndpoint;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RequestBuilderTest {
    private static final DocumentEndpoint documentEnpoint = new DocumentEndpoint();

    @Test
    public void testLoginPassword() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(null, "password");
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("login is missing", e.getMessage());
        }

        requestBuilder = api.initRequest("login", null);
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("password is missing", e.getMessage());
        }
    }

    @Test
    public void testType() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password");
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("type is missing", e.getMessage());
        }

        //should work correctly
        api.initRequest("Login", "password").type("paymentIn").read(documentEnpoint);
    }

    @Test
    public void testLimit() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").limit(-2);
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("limit should be greater than or equal to zero", e.getMessage());
        }

        //should work correctly
        requestBuilder.limit(0).read(documentEnpoint);
        requestBuilder.limit(50).read(documentEnpoint);
    }

    @Test
    public void testOffset() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").offset(-2);
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("offset should be greater than or equal to zero", e.getMessage());
        }

        //should work correctly
        requestBuilder.offset(0).read(documentEnpoint);
        requestBuilder.offset(50).read(documentEnpoint);
    }

    @Test
    public void testExpand() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").expand("demand.agent.owner.cashier");
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("max depth of expand equals 3", e.getMessage());
        }

        //should work correctly
        requestBuilder = api.initRequest("Login", "password").type("paymentIn").expand("demand.agent.owner");
        requestBuilder = api.initRequest("Login", "password").type("paymentIn").expand("demand");
    }
}
