package com.lognex.api;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RequestBuilderTest {
    @Test
    public void testLoginPassword() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(null, "password");
        try {
            requestBuilder.build();
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("login is missing", e.getMessage());
        }

        requestBuilder = api.initRequest("login", null);
        try {
            requestBuilder.build();
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
            requestBuilder.build();
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("type is missing", e.getMessage());
        }

        //should work correctly
        api.initRequest("Login", "password").type("paymentIn").build();
    }

    @Test
    public void testLimit() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").limit(-2);
        try {
            requestBuilder.build();
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("limit should be greater than or equal to zero", e.getMessage());
        }

        //should work correctly
        requestBuilder.limit(0).build();
        requestBuilder.limit(50).build();
    }

    @Test
    public void testOffset() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").offset(-2);
        try {
            requestBuilder.build();
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("offset should be greater than or equal to zero", e.getMessage());
        }

        //should work correctly
        requestBuilder.offset(0).build();
        requestBuilder.offset(50).build();
    }

    @Test
    public void testExpand() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").expand("demand.agent.owner.cashier");
        try {
            requestBuilder.build();
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("max depth of expand equals 3", e.getMessage());
        }

        //should work correctly
        api.initRequest("Login", "password").type("paymentIn").expand("demand.agent.owner").build();
        api.initRequest("Login", "password").type("paymentIn").expand("demand").build();
    }

    @Test
    public void testId() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password").type("paymentIn").id("ac08418c-9482-11e7-7a69-8f550003b1e0");
        //should work correctly
        requestBuilder.build();

        requestBuilder.limit(10);
        try {
            requestBuilder.build();
            fail();
        } catch (IllegalStateException e) {
            TestCase.assertEquals("limit and offset shouldnt be presented when id is presented", e.getMessage());
        }
    }
}
