package com.lognex.api.responses;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.State;

import java.util.List;

/**
 * Список Метаданных Контрагентов
 */
public final class CounterpartyMetadataListResponse {
    public Meta meta;
    public List<Attribute> attributes;
    public List<State> states;
    public List<String> groups;
    public Boolean createShared;
}
