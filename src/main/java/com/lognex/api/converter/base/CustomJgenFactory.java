package com.lognex.api.converter.base;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomJgenFactory  {


    public static CustomJsonGenerator createJsonGenerator(ObjectMapper om, ByteArrayOutputStream outputStream) throws IOException {
        JsonGenerator jsgen = om.getFactory().createGenerator(outputStream);
        return new CustomJsonGenerator(jsgen);
    }
}
