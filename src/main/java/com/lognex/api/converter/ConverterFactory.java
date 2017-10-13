package com.lognex.api.converter;

import com.lognex.api.converter.base.AbstractEntityConverter;
import com.lognex.api.converter.base.Converter;
import com.lognex.api.converter.document.FactureOutConverter;
import com.lognex.api.converter.document.PaymentInConverter;
import com.lognex.api.converter.entity.CounterpartyConverter;
import com.lognex.api.converter.entity.CurrencyConverter;
import com.lognex.api.converter.entity.OrganizationConverter;
import com.lognex.api.converter.entity.ServiceConverter;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Currency;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.model.entity.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class ConverterFactory {

    private static final Map<Class<? extends AbstractEntity>, Class<? extends AbstractEntityConverter>> converters;

    static {
        converters = new LinkedHashMap<>();
        converters.put(Counterparty.class, CounterpartyConverter.class);
        converters.put(Currency.class, CurrencyConverter.class);
        converters.put(PaymentIn.class, PaymentInConverter.class);
        converters.put(Service.class, ServiceConverter.class);
        converters.put(Organization.class, OrganizationConverter.class);
        converters.put(FactureOut.class, FactureOutConverter.class);
    }

    private ConverterFactory() {
    }

    public static Converter<? extends AbstractEntity> getConverter(Class<? extends AbstractEntity> clazz){
        Class<? extends AbstractEntityConverter> converterClass = converters.get(clazz);
        try {
            return (Converter<? extends AbstractEntity>) converterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
