package com.lognex.api.utils;

import com.lognex.api.schema.SchemaMapper;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

public interface TestUtils {

    static String getFile(String fileName){
        String result = "";
        ClassLoader classLoader = SchemaMapper.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get file " + fileName, e);
        }

        return result;
    }
}
