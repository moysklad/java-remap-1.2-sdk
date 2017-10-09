package com.lognex.api.converter.base;

import com.lognex.api.exception.ConverterException;

import java.io.IOException;
import java.util.List;

public interface Converter<T> {

    T convert(String json) throws ConverterException;

    List<T> convertToList(String json) throws ConverterException;

    void toJson(CustomJsonGenerator jgen, T entity) throws IOException;
}
