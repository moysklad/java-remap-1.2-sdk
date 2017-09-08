package com.lognex.api.endpoint;

import com.lognex.api.API;
import com.lognex.api.converter.document.PaymentInConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;

import java.util.List;

public class DocumentEndpoint extends BaseEndpoint {

    public PaymentIn readPaymentIn(API.RequestBuilder rb) throws ConverterException {
        String paymentIn = executeGet(rb.type("paymentin").build(), rb);
        return new PaymentInConverter().convertToEntity(paymentIn);
    }

    public List<PaymentIn> readPaymentInList(API.RequestBuilder rb) throws ConverterException {
        String paymentIn = executeGet(rb.type("paymentin").build(), rb);
        return new PaymentInConverter().convertToListEntity(paymentIn);
    }


}
