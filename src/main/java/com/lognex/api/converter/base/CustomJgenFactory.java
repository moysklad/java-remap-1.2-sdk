package com.lognex.api.converter.base;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CustomJgenFactory  {

    private CustomJgenFactory() {
    }

    public static CustomJsonGenerator createJsonGenerator(ObjectMapper om, ByteArrayOutputStream outputStream) throws IOException {
        JsonGenerator jsgen = om.getFactory().createGenerator(outputStream);
        return new CustomJsonGenerator(jsgen);
    }
}
