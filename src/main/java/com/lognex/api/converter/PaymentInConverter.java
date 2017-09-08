package com.lognex.api.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.base.AbstractFinanceInConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentInConverter extends AbstractFinanceInConverter implements Converter<PaymentIn> {

    @Override
    public PaymentIn convertToEntity(String response) {
        return null;
    }

    private PaymentIn convertToEntity(JsonNode node) throws ConverterException {
        PaymentIn result = new PaymentIn();
        super.convertToEntity(result, node);
        return result;
    }

    @Override
    public List<PaymentIn> convertToListEntity(String response) throws ConverterException {
        int size;
        JsonNode rows;
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBytes());
            size = root.get("meta").get("size").asInt();
            rows = root.get("rows");
        } catch (IOException e) {
            throw new ConverterException(e);
        }

        List<PaymentIn> result = new ArrayList<>(size);

        for (int i = 0; i < size ; i++) {
            result.add(convertToEntity(rows.get(i)));
        }
        return result;
    }
}
