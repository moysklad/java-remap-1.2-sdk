package com.lognex.api.converter;

import com.lognex.api.exception.ConverterException;

import java.util.List;

public interface Converter<T> {

    T convert(String response) throws ConverterException;

    List<T> convertToList(String response) throws ConverterException;
}
