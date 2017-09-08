package com.lognex.api.converter;

import com.lognex.api.exception.ConverterException;

import java.util.List;

public interface Converter<T> {

    T convertToEntity(String response);

    List<T> convertToListEntity(String response) throws ConverterException;
}
