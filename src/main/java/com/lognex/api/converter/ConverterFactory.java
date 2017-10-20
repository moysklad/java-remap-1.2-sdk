package com.lognex.api.converter;

import com.lognex.api.converter.base.EntityConverter;
import com.lognex.api.converter.base.Converter;
import com.lognex.api.converter.document.FactureOutConverter;
import com.lognex.api.converter.base.ShipmentOutPositionConverter;
import com.lognex.api.converter.document.DemandConverter;
import com.lognex.api.converter.document.PaymentInConverter;
import com.lognex.api.converter.entity.AgentAccountConverter;
import com.lognex.api.converter.entity.CounterpartyConverter;
import com.lognex.api.converter.entity.CurrencyConverter;
import com.lognex.api.converter.entity.OrganizationConverter;
import com.lognex.api.converter.entity.ServiceConverter;
import com.lognex.api.converter.entity.*;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.model.base.ShipmentOutPosition;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.AgentAccount;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Currency;
import com.lognex.api.model.entity.Organization;
import com.lognex.api.model.entity.good.Service;
import com.lognex.api.model.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterFactory {

    private static final Map<Class<? extends Entity>, Class<? extends EntityConverter>> converters;

    static {
        converters = new HashMap<>();
        converters.put(AgentAccount.class, AgentAccountConverter.class);
        converters.put(Contract.class, ContractConverter.class);
        converters.put(Counterparty.class, CounterpartyConverter.class);
        converters.put(Currency.class, CurrencyConverter.class);
        converters.put(Service.class, ServiceConverter.class);
        converters.put(Project.class, ProjectConverter.class);
        converters.put(Organization.class, OrganizationConverter.class);
        converters.put(FactureOut.class, FactureOutConverter.class);
        converters.put(Store.class, StoreConverter.class);

        converters.put(Demand.class, DemandConverter.class);
        converters.put(PaymentIn.class, PaymentInConverter.class);

        converters.put(ShipmentOutPosition.class, ShipmentOutPositionConverter.class);
    }

    @SuppressWarnings("unchecked")
    public static Converter<? extends Entity> getConverter(Class<? extends Entity> clazz){
        Class<? extends EntityConverter> converterClass = converters.get(clazz);
        try {
            return (Converter<? extends Entity>) converterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
